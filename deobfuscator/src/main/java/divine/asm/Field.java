package divine.asm;

import divine.asm.enums.UsedStatus;
import org.objectweb.asm.tree.FieldNode;

/**
 * Author: Typically
 * Project: divine.asm
 * Date: 1/28/2019
 */
public class Field {

    private ClassFile owner;
    private FieldNode node;

    private UsedStatus used = UsedStatus.UNKNOWN; //default unused

    //ASM Variables
    private String name;
    private String desc;
    private int access;

    public Field(ClassFile owner, FieldNode node) {
        this.owner = owner;
        this.node = node;

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

    public FieldNode getNode() {
        return node;
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

    @Override
    public String toString() {
        return String.format("%s.%s %s", owner.getName(), node.name, node.desc);
    }
}
