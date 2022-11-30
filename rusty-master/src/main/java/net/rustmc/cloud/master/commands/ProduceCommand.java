package net.rustmc.cloud.master.commands;

import net.rustmc.cloud.api.commands.Command;
import net.rustmc.cloud.master.RustCloud;

import javax.lang.model.util.Elements;
import java.text.ParseException;
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
            } else if (args[1].equals("group")) {
                if (args.length == 9) {
                    try {
                        RustCloud.getCloud().getGroupTerminal().produce(
                                args[2],
                                Boolean.parseBoolean(args[3]),
                                Integer.parseInt(args[4]),
                                Integer.parseInt(args[5]),
                                Integer.parseInt(args[6]),
                                Integer.parseInt(args[7]),
                                args[8]
                        );
                        RustCloud.getCloud().getCloudConsole().send("the " + args[2] + " group has been §asuccessfully §rcreated.");
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                } else if (args.length == 8) {
                    try {
                        final var object = RustCloud.getCloud().getGroupTerminal().produce(
                                args[2],
                                Boolean.parseBoolean(args[3]),
                                Integer.parseInt(args[4]),
                                Integer.parseInt(args[5]),
                                Integer.parseInt(args[6]),
                                Integer.parseInt(args[7]),
                                "null"
                        );
                        RustCloud.getCloud().getCloudConsole().send("the " + args[2] + " group has been §asuccessfully §rcreated on " + object.getObject().getAllocatedNode() + ".");
                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
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
                if (line.split(" ")[1].equals("node")) {
                    candidates.add(resultForNodes());
                } else {
                    candidates.add("name");
                }
            }
            case 3 -> {
                if (line.split(" ")[1].equals("node")) {
                    candidates.add("max.groups");
                } else {
                    candidates.add("is.proxy");
                }
            }
            case 4 -> {
                if (line.split(" ")[1].equals("group")) {
                    candidates.add("max.players");
                }
            }
            case 5 -> {
                if (line.split(" ")[1].equals("group")) {
                    candidates.add("percent");
                }
            }
            case 6 -> {
                if (line.split(" ")[1].equals("group")) {
                    candidates.add("max.services");
                }
            }
            case 7 -> {
                if (line.split(" ")[1].equals("group")) {
                    candidates.add("memory");
                }
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
