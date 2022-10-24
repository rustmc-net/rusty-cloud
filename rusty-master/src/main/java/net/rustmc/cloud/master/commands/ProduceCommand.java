package net.rustmc.cloud.master.commands;

import net.rustmc.cloud.api.commands.Command;

import java.util.List;

/**
 * this file belongs to the rusty-cloud project.
 *
 * @author Alexander Jilge
 * @since 24.10.2022, Mo.
 */
public class ProduceCommand extends Command {

    protected ProduceCommand(String name, String... aliases) {
        super("produce", "command to provide objects", new String[]{"create", "provide"});
    }

    @Override
    public void execute(String[] args) {

    }

    @Override
    public List<String> onTab(int pos) {
        return null;
    }
}
