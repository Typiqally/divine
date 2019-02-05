package divine.deobfuscation;

import divine.asm.ClassGroup;
import divine.asm.flow.FlowFixer;
import divine.asm.util.JarUtil;
import divine.deobfuscation.components.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * Author: Typically
 * Project: divine.deobfuscation
 * Date: 1/28/2019
 */
public class Deobfuscator {

    private final static Logger logger = LoggerFactory.getLogger(Deobfuscator.class);
    private final File inputFile;
    private final File outputFile;
    private ClassGroup group;
    private FlowFixer fixer;

    public Deobfuscator(File inputFile, File outputFile) throws IOException {
        this.inputFile = inputFile;
        this.outputFile = outputFile;

        construct();
    }

    private void construct() throws IOException {
        group = JarUtil.load(inputFile);
        fixer = new FlowFixer(group);
    }

    public void run() throws IOException {
        fixer.run();

        run(new RuntimeExceptions(group));

        run(new UnusedFields(group));
        run(new UnusedMethods(group));
        run(new UnusedClass(group));

        run(new OpaquePredicates(group));

        finish();
    }

    public void run(DeobfuscatorComponent component) {
        component.run();

        component.logger.info("Modified {} nodes", component.modified);
    }

    private void finish() throws IOException {
        group.update();

        JarUtil.write(group, outputFile);
    }

    public File getInputFile() {
        return inputFile;
    }

    public File getOutputFile() {
        return outputFile;
    }
}
