package divine.deobfuscation;

import divine.asm.ClassGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author: Typically
 * Project: divine.deobfuscation
 * Date: 1/28/2019
 */
public abstract class DeobfuscatorComponent {

    protected ClassGroup group;

    protected int modified = -1;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public DeobfuscatorComponent(ClassGroup group) {
        this.group = group;
    }

    public abstract void run();

    public boolean isObfuscated(String name) {
        return name.length() <= 2 || name.startsWith("method") || name.startsWith("vmethod") || name.startsWith("field") || name.startsWith("class");
    }

}
