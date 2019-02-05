package divine.deobfuscation.components;

import divine.asm.ClassFile;
import divine.asm.ClassGroup;
import divine.asm.Method;
import divine.deobfuscation.DeobfuscatorComponent;
import org.objectweb.asm.tree.TryCatchBlockNode;

import java.util.ArrayList;

/**
 * Author: Typically
 * Project: divine.deobfuscation.components
 * Date: 1/28/2019
 */
public class RuntimeExceptions extends DeobfuscatorComponent {

    public RuntimeExceptions(ClassGroup group) {
        super(group);
    }

    @Override
    public void run() {
        for (ClassFile classFile : group.getClasses())
            for (Method method : classFile.getMethods()) {
                if (classFile.getName().equals("client") && method.getName().equals("init"))
                    continue;

                for (TryCatchBlockNode tryCatchBlockNode : new ArrayList<>(method.getNode().tryCatchBlocks))
                    if (tryCatchBlockNode.type != null && tryCatchBlockNode.type.equals("java/lang/RuntimeException")) {
                        method.getNode().tryCatchBlocks.remove(tryCatchBlockNode);
                        modified++;
                    }
            }
    }
}
