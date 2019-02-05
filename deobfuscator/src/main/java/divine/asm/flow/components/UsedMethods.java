package divine.asm.flow.components;

import divine.asm.ClassFile;
import divine.asm.ClassGroup;
import divine.asm.Method;
import divine.asm.enums.UsedStatus;
import divine.asm.flow.FlowFixerComponent;

/**
 * Author: Typically
 * Project: divine.asm.flow.components
 * Date: 2/4/2019
 */
public class UsedMethods extends FlowFixerComponent {

    public UsedMethods(ClassGroup group) {
        super(group);
    }

    @Override
    public void run() {
        for (ClassFile classFile : group.getClasses())
            for (Method method : classFile.getMethods())
                for (Method methodInvoked : method.getGraph().getAllMethodInvokes()) {
                    if (!methodInvoked.getUsed().equals(UsedStatus.USED))
                        methodInvoked.setUsed(UsedStatus.USED);
                    modified++;
                }
    }
}
