package divine.asm;

import org.objectweb.asm.tree.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Typically
 * Project: divine.asm
 * Date: 1/28/2019
 */
public class ClassFile {

    private ClassGroup group;
    private ClassNode node;

    private List<Field> fields = new ArrayList<>();
    private List<Method> methods = new ArrayList<>();

    //ASM Variables
    private String name;
    private String superName;
    private List<FieldNode> fieldNodes;
    private List<MethodNode> methodNodes;
    private List<String> interfaces;

    public ClassFile(ClassGroup group, ClassNode node) {
        this.group = group;
        this.node = node;

        construct();
        wrap();
    }

    private void construct() {
        name = node.name;
        superName = node.superName;
        fieldNodes = node.fields;
        methodNodes = node.methods;
        interfaces = node.interfaces;
    }

    private void wrap() {
        for (FieldNode fieldNode : fieldNodes)
            fields.add(new Field(this, fieldNode));

        for (MethodNode methodNode : methodNodes)
            methods.add(new Method(this, methodNode));
    }

    public void updateAll() {
        updateFields();
        updateMethods();
    }

    public void updateFields() {
        fieldNodes.clear();

        for (Field field : fields)
            fieldNodes.add(field.getNode());
    }

    public void updateMethods() {
        methodNodes.clear();

        for (Method method : methods)
            methodNodes.add(method.getNode());
    }

    public Field getField(String owner, String name, String desc) {
        ClassFile classFile = group.getClassByName(owner);

        while (true) {
            if (classFile == null || owner.startsWith("java"))
                return null;

            for (Field field : classFile.getFields())
                if (field.getNode().name.equals(name) && field.getNode().desc.equals(desc))
                    return field;

            if (classFile.getSuperName() != null)
                classFile = group.getClassByName(classFile.getSuperName());
        }
    }

    public Method getMethod(String owner, String name, String desc) {
        ClassFile classFile = group.getClassByName(owner);

        while (true) {
            if (classFile == null)
                return null;

            for (Method method : classFile.methods)
                if (method.getName().equals(name) && method.getDesc().equals(desc))
                    return method;

            if (classFile.getSuperName() != null)
                classFile = group.getClassByName(classFile.getSuperName());
        }
    }

    public Field getFieldFromInsn(FieldInsnNode insn) {
        return getField(insn.owner, insn.name, insn.desc);
    }

    public Method getMethodFromInsn(MethodInsnNode insn) {
        return getMethod(insn.owner, insn.name, insn.desc);
    }

    public ClassGroup getGroup() {
        return group;
    }

    public ClassNode getNode() {
        return node;
    }

    public List<ClassFile> getInterfaceClasses() {
        List<ClassFile> classFiles = new ArrayList<>();
        for (String implementable : interfaces)
            if (!implementable.contains("java"))
                classFiles.add(group.getClassByName(implementable));
        return classFiles;
    }

    public List<Field> getFields() {
        return fields;
    }

    public List<Method> getMethods() {
        return methods;
    }

    public String getName() {
        return name;
    }

    public String getSuperName() {
        return superName;
    }

    public List<String> getInterfaces() {
        return interfaces;
    }

    public ClassFile getSuperClass() {
        return group.getClassByName(superName);
    }

}
