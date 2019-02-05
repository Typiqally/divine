package divine.boot;

import divine.client.core.Divine;
import divine.client.game.GamePack;
import divine.deobfuscation.Deobfuscator;
import org.apache.commons.cli.*;

import java.io.File;
import java.io.IOException;

/**
 * Author: Typically
 * Project: divine.boot
 * Date: 1/29/2019
 */
public class Boot {

    private final Options options = new Options();
    private final CommandLineParser parser = new DefaultParser();
    private final HelpFormatter formatter = new HelpFormatter();
    private final String[] args;
    private CommandLine line = null;

    private Boot(String[] args) throws IOException {
        this.args = args;

        build();
        parse();
    }

    public static void main(String[] args) throws Exception {
        if (args.length > 0)
            new Boot(args);

        new Divine(new GamePack(0, new File(System.getProperty("user.home"), ".divine/gamepack_out.jar"))).init();
    }

    private void build() {
        Option vanilla = new Option("v", "vanilla", false, "Launch vanilla client.");
        options.addOption(vanilla);

        Option deobfuscate = new Option("d", "deobfuscate", true, "Deobfuscate game pack");
        deobfuscate.setArgName("input, output");
        deobfuscate.setArgs(2);
        options.addOption(deobfuscate);
    }

    private void parse() throws IOException {
        try {
            line = parser.parse(options, args);
        } catch (ParseException e) {
            formatter.printHelp("Boot", options);
        }

        if (line == null)
            return;

        if (line.hasOption("deobfuscate")) {
            String[] args = line.getOptionValues("deobfuscate");
            new Deobfuscator(new File(args[0]), new File(args[1])).run();
        }
    }

}
