package divine.asm.flow;

import divine.asm.ClassFile;
import divine.asm.ClassGroup;
import divine.asm.Method;
import divine.asm.flow.components.BlockOrder;
import divine.asm.flow.components.UsedClass;
import divine.asm.flow.components.UsedFields;
import divine.asm.flow.components.UsedMethods;
import divine.asm.flow.graph.ControlFlowGraph;
import divine.deobfuscation.Deobfuscator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Author: Typically
 * Project: divine.asm.flow
 * Date: 2/4/2019
 */
public class FlowFixer {

    private final static Logger logger = LoggerFactory.getLogger(Deobfuscator.class);
    private final ClassGroup group;

    public FlowFixer(ClassGroup group) {
        this.group = group;

        initialize();
    }

    private void initialize() {
        flow();
    }

    private void flow() {
        for (ClassFile classFile : group.getClasses())
            for (Method method : classFile.getMethods())
                method.setGraph(new ControlFlowGraph(method));
    }

    public void run() {
        run(new UsedFields(group));
        run(new UsedMethods(group));
        run(new UsedClass(group));

        run(new BlockOrder(group));
    }

    private void run(FlowFixerComponent component) {
        component.run();

        component.logger.info("Modified and or analyzed {} nodes", component.modified);
    }

    public ClassGroup getGroup() {
        return group;
    }
}
