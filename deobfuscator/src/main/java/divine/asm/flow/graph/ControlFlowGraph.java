package divine.asm.flow.graph;

import divine.asm.Block;
import divine.asm.Field;
import divine.asm.Method;
import org.objectweb.asm.Label;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Typically
 * Project: divine.asm.flow.graph
 * Date: 1/28/2019
 */
public class ControlFlowGraph {

    private final Method owner;
    private final HashMap<Block, List<Field>> fieldCalls = new HashMap<>();
    private final HashMap<Block, List<Method>> methodInvokes = new HashMap<>();
    private Block head;
    private List<Block> blocks = new ArrayList<>();

    public ControlFlowGraph(Method owner) {
        this.owner = owner;

        construct();
    }

    private void construct() {
        constructBlocks();

        flowFieldCalls();
        flowMethodInvokes();

        if (owner.getOwner().getName().equals("ae") && owner.getName().equals("c") && head != null)
            fixBlocks();
    }

    private void constructBlocks() {
        for (AbstractInsnNode abstractInsnNode : owner.getInstructions()) {
            if (!(abstractInsnNode instanceof LabelNode))
                continue;

            Block block = new Block(owner, ((LabelNode) abstractInsnNode).getLabel());
            exploreBlock(block, abstractInsnNode);

            blocks.add(block);
        }

        if (blocks.size() > 0)
            head = blocks.get(0);
    }

    private void exploreBlock(Block block, AbstractInsnNode labelNode) {
        if (!(labelNode instanceof LabelNode))
            return;

        boolean blockReachedEnd = false;
        AbstractInsnNode nextInsn = labelNode;

        block.getInstructions().add(nextInsn);
        while (!blockReachedEnd) {
            nextInsn = nextInsn.getNext();

            if (nextInsn == null || nextInsn instanceof LabelNode)
                blockReachedEnd = true;
            else
                block.getInstructions().add(nextInsn);
        }
    }

    private void flowFieldCalls() {
        for (Block block : blocks) {
            List<Field> fieldCalls = new ArrayList<>();
            for (AbstractInsnNode insn : block.getInstructions())
                if (insn instanceof FieldInsnNode) {
                    Field field = owner.getOwner().getFieldFromInsn((FieldInsnNode) insn);
                    if (field != null)
                        fieldCalls.add(field);
                }
            this.fieldCalls.put(block, fieldCalls);
        }
    }

    private void flowMethodInvokes() {
        for (Block block : blocks) {
            List<Method> methodInvokes = new ArrayList<>();
            for (AbstractInsnNode insn : block.getInstructions())
                if (insn instanceof MethodInsnNode) {
                    Method method = owner.getOwner().getMethodFromInsn((MethodInsnNode) insn);
                    if (method != null)
                        methodInvokes.add(method);
                }
            this.methodInvokes.put(block, methodInvokes);
        }
    }

    private void fixBlocks() {
        for (int i = 0; i < blocks.size(); i++) {
            List<AbstractInsnNode> instructions = blocks.get(i).getInstructions();
            AbstractInsnNode lastInsn = instructions.get(instructions.size() - 1);
            int opcode = lastInsn.getOpcode();
            if (opcode != Opcodes.GOTO && opcode != Opcodes.ATHROW && (opcode < Opcodes.IRETURN || opcode > Opcodes.RETURN))
                instructions.add(new JumpInsnNode(Opcodes.GOTO, new LabelNode(blocks.get(i + 1).getLabel())));
        }
    }

    public Method getOwner() {
        return owner;
    }

    public Block getHead() {
        return head;
    }

    public List<Block> getBlocks() {
        return blocks;
    }

    public HashMap<Block, List<Field>> getFieldCalls() {
        return fieldCalls;
    }

    public HashMap<Block, List<Method>> getMethodInvokes() {
        return methodInvokes;
    }

    public Block getBlockByLabel(Label label) {
        for (Block block : blocks)
            if (block.getLabel().equals(label))
                return block;
        return null;
    }

    public List<Field> getFieldAllCalls() {
        List<Field> fields = new ArrayList<>();
        for (Map.Entry<Block, List<Field>> blockEntry : fieldCalls.entrySet())
            fields.addAll(blockEntry.getValue());
        return fields;
    }

    public List<Method> getAllMethodInvokes() {
        List<Method> methods = new ArrayList<>();
        for (Map.Entry<Block, List<Method>> blockEntry : methodInvokes.entrySet())
            methods.addAll(blockEntry.getValue());
        return methods;
    }
}
