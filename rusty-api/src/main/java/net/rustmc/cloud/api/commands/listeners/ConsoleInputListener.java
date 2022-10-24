package net.rustmc.cloud.api.commands.listeners;

import net.rustmc.cloud.api.commands.Command;
import net.rustmc.cloud.api.commands.CommandManager;
import net.rustmc.cloud.base.common.Rust;
import net.rustmc.cloud.base.common.events.natives.CloudNativeConsoleInputEvent;

import java.util.LinkedList;
import java.util.List;

/**
 * This file is a JavaDoc!
 * Created: 10/23/2022
 *
 * @author RedCrew
 * Discord: RedCrew#0100
 * Website: https://redcrewtv.de/
 */
public class ConsoleInputListener {

    public ConsoleInputListener(final CommandManager commandManager) {
        Rust.getInstance().getListenerBus().register(CloudNativeConsoleInputEvent.class, event -> {
            String cmd = event.getInput().split(" ")[0];

            for (Command command : commandManager.getCommands()) {
                if(command.getName().equalsIgnoreCase(cmd)) {
                    command.execute(event.getArguments());
                    return;
                }
                if(command.getAliases() != null) {
                    for (String alias : command.getAliases()) {
                        if(alias.equalsIgnoreCase(cmd)) {
                            command.execute(event.getArguments());
                        }
                    }
                }
            }
        });
    }
}
