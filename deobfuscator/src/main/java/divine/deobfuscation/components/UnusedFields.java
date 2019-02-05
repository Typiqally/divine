package divine.deobfuscation.components;

import divine.asm.ClassFile;
import divine.asm.ClassGroup;
import divine.asm.Field;
import divine.asm.enums.UsedStatus;
import divine.deobfuscation.DeobfuscatorComponent;

import java.util.ArrayList;

/**
 * Author: Typically
 * Project: divine.deobfuscation.components
 * Date: 1/28/2019
 */
public class UnusedFields extends DeobfuscatorComponent {

    public UnusedFields(ClassGroup group) {
        super(group);
    }

    @Override
    public void run() {
        for (ClassFile classFile : group.getClasses())
            for (Field field : new ArrayList<>(classFile.getFields()))
                if (isObfuscated(field.getName()) && !field.getUsed().equals(UsedStatus.USED)) {
                    classFile.getFields().remove(field);
                    modified++;
                }
    }
}
