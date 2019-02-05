package divine.asm.flow.components;

import divine.asm.ClassFile;
import divine.asm.ClassGroup;
import divine.asm.Field;
import divine.asm.Method;
import divine.asm.enums.UsedStatus;
import divine.asm.flow.FlowFixerComponent;

/**
 * Author: Typically
 * Project: divine.asm.flow.components
 * Date: 2/4/2019
 */
public class UsedFields extends FlowFixerComponent {

    public UsedFields(ClassGroup group) {
        super(group);
    }

    @Override
    public void run() {
        for (ClassFile classFile : group.getClasses())
            for (Method method : classFile.getMethods())
                for (Field field : method.getGraph().getFieldAllCalls()) {
                    if (!field.getUsed().equals(UsedStatus.USED))
                        field.setUsed(UsedStatus.USED);
                    modified++;
                }
    }
}
