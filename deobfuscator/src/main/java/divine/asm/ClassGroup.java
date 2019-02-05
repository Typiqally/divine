package divine.asm;

import org.objectweb.asm.tree.ClassNode;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author: Typically
 * Project: divine.asm
 * Date: 1/28/2019
 */
public class ClassGroup {

    private final List<ClassNode> nodes;
    private final List<ClassFile> classes = new ArrayList<>();

    public ClassGroup() {
        this.nodes = new ArrayList<>();
    }

    public ClassGroup(List<ClassNode> nodes) {
        this.nodes = nodes;

        initialize();
    }

    public void initialize() {
        construct();
    }

    private void construct() {
        for (ClassNode classNode : nodes)
            classes.add(new ClassFile(this, classNode));
    }

    public void update() {
        for (ClassFile classFile : classes)
            classFile.updateAll();
    }

    public void addNode(ClassNode classNode) {
        nodes.add(classNode);
    }

    public ClassFile getClassByName(String name) {
        for (ClassFile classFile : classes)
            if (classFile.getName().equals(name))
                return classFile;
        return null;
    }

    public List<ClassNode> getNodes() {
        return nodes;
    }

    public List<ClassFile> getClasses() {
        return Collections.unmodifiableList(classes);
    }
}
