package divine.deobfuscation.components;

import divine.asm.ClassFile;
import divine.asm.ClassGroup;
import divine.asm.Method;
import divine.asm.enums.UsedStatus;
import divine.deobfuscation.DeobfuscatorComponent;

import java.util.ArrayList;

/**
 * Author: Typically
 * Project: divine.deobfuscation.components
 * Date: 1/28/2019
 */
public class UnusedMethods extends DeobfuscatorComponent {
    public UnusedMethods(ClassGroup group) {
        super(group);
    }

    @Override
    public void run() {
        for (ClassFile classFile : group.getClasses())
            for (Method method : new ArrayList<>(classFile.getMethods()))
                if (isObfuscated(method.getName()) && !method.isImplemented() && !method.getName().contains("init") && !method.getUsed().equals(UsedStatus.USED)) {
                    classFile.getMethods().remove(method);
                    modified++;
                }
    }
}
