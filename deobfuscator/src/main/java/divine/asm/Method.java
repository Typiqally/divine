package divine.asm;

import divine.asm.enums.UsedStatus;
import divine.asm.flow.graph.ControlFlowGraph;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.MethodNode;

/**
 * Author: Typically
 * Project: divine.asm
 * Date: 1/28/2019
 */
public class Method {

    private final ClassFile owner;
    private final MethodNode node;
    private final AbstractInsnNode[] instructions;

    private UsedStatus used = UsedStatus.UNKNOWN; //default unused

    //ASM Variables
    private String name;
    private String desc;
    private int access;

    private ControlFlowGraph graph;

    public Method(ClassFile owner, MethodNode node) {
        this.owner = owner;
        this.node = node;
        this.instructions = node.instructions.toArray();

        construct();
    }

    private void construct() {
        name = node.name;
        desc = node.desc;
        access = node.access;
    }

    public ClassFile getOwner() {
        return owner;
    }

    public MethodNode getNode() {
        return node;
    }

    public AbstractInsnNode[] getInstructions() {
        return instructions;
    }

    public UsedStatus getUsed() {
        return used;
    }

    public void setUsed(UsedStatus used) {
        this.used = used;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }

    public int getAccess() {
        return access;
    }

    public ControlFlowGraph getGraph() {
        return graph;
    }

    public void setGraph(ControlFlowGraph graph) {
        this.graph = graph;
    }

    public boolean isImplemented() {
        ClassFile superClassFile = owner.getSuperClass();

        while (superClassFile != null) {
            for (Method method : superClassFile.getMethods())
                if (method.getUsed().equals(UsedStatus.USED))
                    if (method.name.equals(name) && method.desc.equals(desc))
                        return true;

            superClassFile = superClassFile.getSuperClass();
        }

        for (ClassFile classFile : owner.getInterfaceClasses())
            for (Method method : classFile.getMethods())
                if (method.name.equals(name) && method.desc.equals(desc))
                    return true;

        return false;
    }

    @Override
    public String toString() {
        return String.format("%s.%s %s", owner.getName(), node.name, node.desc);
    }
}
