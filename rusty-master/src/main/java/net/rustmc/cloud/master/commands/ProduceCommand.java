package net.rustmc.cloud.master.commands;

import net.rustmc.cloud.api.commands.Command;
import net.rustmc.cloud.api.objects.SimpleCloudNode;
import net.rustmc.cloud.base.common.Rust;
import net.rustmc.cloud.base.console.ICloudConsole;
import net.rustmc.cloud.master.RustCloud;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * this file belongs to the rusty-cloud project.
 *
 * @author Alexander Jilge
 * @since 24.10.2022, Mo.
 */
@SuppressWarnings("SwitchStatementWithTooFewBranches")
public class ProduceCommand extends Command {

    public ProduceCommand() {
        super("produce", "command to provide objects", new String[]{"create", "provide"});
    }

    @Override
    public void execute(String[] args) {
        if (args != null) {
            if (args.length != 1) {
                switch (args[1]) {
                    case "node" -> {
                        if (args.length == 5) {
                            final var node = new SimpleCloudNode(args[2], args[3], Integer.parseInt(args[4]));
                            RustCloud.getCloud().getNodeManager().register(node);
                            RustCloud.getCloud().getCloudConsole().send("The node §a" + node.getName() + " §ris successfully created");
                            if (RustCloud.getCloud().getConfiguration().isAutoConfigurationUpdater())
                                Rust.getInstance().getConfigurationHandler().update(node.getName());
                        } else RustCloud.getCloud().getCloudConsole().send("this arguments can not be handled", ICloudConsole.Output.ERROR);
                    }
                    default -> {
                        RustCloud.getCloud().getCloudConsole().send("this arguments can not be handled", ICloudConsole.Output.ERROR);
                    }
                }
            } else {
                RustCloud.getCloud().getCloudConsole().send("this arguments can not be handled", ICloudConsole.Output.ERROR);
            }
        } else {
            RustCloud.getCloud().getCloudConsole().send("this arguments can not be handled", ICloudConsole.Output.ERROR);
        }
    }

    @Override
    public List<String> onTab(final int pos, final String line) {
        final var candidates = new ArrayList<String>();
        if (pos == 1) {
            candidates.add("node");
            candidates.add("group");
        } else {
            final var words = line.split(" ");
            if (pos == 2) {
                if (words[1].equalsIgnoreCase("node")) {
                    candidates.add("node-1");
                }
            } else if (pos == 3) {
                if (words[1].equalsIgnoreCase("node")) {
                    candidates.add("127.0.0.1");
                }
            } else if (pos == 4) {
                if (words[1].equalsIgnoreCase("node")) {
                    candidates.add("2024");
                }
            }
        }
        return candidates;
    }
}
