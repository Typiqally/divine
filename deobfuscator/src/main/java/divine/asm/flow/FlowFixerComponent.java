package divine.asm.flow;

import divine.asm.ClassGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author: Typically
 * Project: divine.asm.flow
 * Date: 2/4/2019
 */
public abstract class FlowFixerComponent {

    protected ClassGroup group;

    protected int modified = -1;

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    public FlowFixerComponent(ClassGroup group) {
        this.group = group;
    }

    public abstract void run();

}
