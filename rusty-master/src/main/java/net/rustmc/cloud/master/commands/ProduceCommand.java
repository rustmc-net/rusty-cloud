package net.rustmc.cloud.master.commands;

import net.rustmc.cloud.api.commands.Command;
import net.rustmc.cloud.api.objects.SimpleCloudNode;
import net.rustmc.cloud.base.console.ICloudConsole;
import net.rustmc.cloud.master.RustCloud;

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
            if (args.length != 0) {
                switch (args[0]) {
                    case "node" -> {
                        if (args.length == 4) {
                            final var node = new SimpleCloudNode(args[1], args[2], Integer.parseInt(args[3]));
                            RustCloud.getCloud().getNodeManager().register(node);
                            RustCloud.getCloud().getCloudConsole().send("The node §a" + node.getName() + " §ris successfully created");
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
    public List<String> onTab(int pos) {
        return null;
    }
}
