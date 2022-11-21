package net.rustmc.cloud.master.commands;

import net.rustmc.cloud.api.commands.Command;
import net.rustmc.cloud.master.RustCloud;

import javax.lang.model.util.Elements;
import java.util.ArrayList;
import java.util.List;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 13.11.2022
 */
public class ProduceCommand extends Command {

    public ProduceCommand() {
        super("produce");
    }

    @Override
    public void execute(String[] args) {
        if (args != null) {
            if (args[1].equals("node")) {
                if (args.length == 4) {
                    RustCloud.getCloud().getOfflineNodeTerminal().newOfflineNode(args[2], Integer.parseInt(args[3]));
                    RustCloud.getCloud().getCloudConsole().send("The node " + args[2] + " §ris §asuccessfully §rcreated.");
                }
            }
        }
    }

    @Override
    public List<String> onTab(int pos, String line) {
        final List<String> candidates = new ArrayList<>();
        switch (pos) {
            case 1 -> {
                candidates.add("node");
                candidates.add("group");
            }
            case 2 -> {
                candidates.add(resultForNodes());
            }
            case 3 -> {
                candidates.add("max.groups");
            }
        }
        return candidates;
    }

    @SuppressWarnings("DataFlowIssue")
    public String resultForNodes() {
        if (RustCloud.getCloud().getNodeFile().listFiles() != null) {
            return "node-" + (RustCloud.getCloud().getNodeFile().listFiles().length+1);
        } else return "node-1";
    }

}
