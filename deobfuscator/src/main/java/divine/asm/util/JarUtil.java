package divine.asm.util;

import divine.asm.ClassGroup;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.util.CheckClassAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;

/**
 * Author: Typically
 * Project: divine.asm.util
 * Date: 1/29/2019
 */
public class JarUtil {

    public static ClassGroup load(File jarFile) throws IOException {
        ClassGroup classGroup = new ClassGroup();

        try (JarFile jar = new JarFile(jarFile)) {

            for (Enumeration<JarEntry> entries = jar.entries(); entries.hasMoreElements(); ) {
                JarEntry entry = entries.nextElement();

                if (!entry.getName().endsWith(".class"))
                    continue;

                ClassNode classNode = new ClassNode();

                try (InputStream classFileInputStream = jar.getInputStream(entry)) {
                    ClassReader classReader = new ClassReader(classFileInputStream);
                    classReader.accept(classNode, ClassReader.SKIP_FRAMES);
                }

                classGroup.addNode(classNode);
            }
        }

        classGroup.initialize();

        return classGroup;
    }

    public static void write(ClassGroup group, File outputFile) throws IOException {
        try (JarOutputStream jos = new JarOutputStream(new FileOutputStream(outputFile))) {
            for (ClassNode classNode : group.getNodes()) {
                ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
                classNode.accept(new CheckClassAdapter(writer, true));

                byte[] bytes = writer.toByteArray();
                jos.putNextEntry(new JarEntry(classNode.name.concat(".class")));
                jos.write(bytes);
            }
        }
    }

}
