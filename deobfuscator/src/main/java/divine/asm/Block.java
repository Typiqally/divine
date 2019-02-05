package divine.asm;

import org.objectweb.asm.Label;
import org.objectweb.asm.tree.AbstractInsnNode;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: Typically
 * Project: divine.asm
 * Date: 1/29/2019
 */
public class Block {

    private final Method owner;
    private Label label;

    private List<AbstractInsnNode> instructions = new ArrayList<>();

    public Block(Method owner, Label label) {
        this.owner = owner;
        this.label = label;
    }

    public Method getOwner() {
        return owner;
    }

    public Label getLabel() {
        return label;
    }

    public void setLabel(Label label) {
        this.label = label;
    }

    public List<AbstractInsnNode> getInstructions() {
        return instructions;
    }
}
