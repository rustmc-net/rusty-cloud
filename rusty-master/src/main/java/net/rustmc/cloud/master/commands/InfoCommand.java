package net.rustmc.cloud.master.commands;

import net.rustmc.cloud.api.commands.Command;
import net.rustmc.cloud.master.RustCloud;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 21.11.2022
 */
public class InfoCommand extends Command {

    public InfoCommand() {
        super("info", "meta");
    }

    @Override
    public void execute(String[] args) {
        if (args != null) {
            if (args[1].equals("pool")) {
                RustCloud.getCloud().getCloudConsole().send("connected nodes: §e" + RustCloud.getCloud().getOnlineNodeTerminal().size() + "§r.");
                RustCloud.getCloud().getCloudConsole().send("offline nodes: §e" + RustCloud.getCloud().getOfflineNodeTerminal().size() + "§r.");
            }
        }
    }

    @Override
    public List<String> onTab(int pos, String line) {
        final List<String> candidates = new ArrayList<>();
        if (pos == 1) {
            candidates.add("pool");
        }
        return candidates;
    }
}
