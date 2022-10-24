package net.rustmc.cloud.master.commands;

import net.rustmc.cloud.api.commands.Command;

import java.util.List;

/**
 * this file belongs to the rusty-cloud project.
 *
 * @author Alexander Jilge
 * @since 24.10.2022, Mo.
 */
public class CloseCommand extends Command {

    public CloseCommand() {
        super("close", "to close the cloud", new String[]{"shutdown", "stop"});
    }

    @Override
    public void execute(String[] args) {
        System.exit(0);
    }

    @Override
    public List<String> onTab(int pos) {
        return null;
    }

}
