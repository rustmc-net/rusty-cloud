package net.rustmc.cloud.api.commands.listeners;

import net.rustmc.cloud.api.commands.Command;
import net.rustmc.cloud.api.commands.CommandManager;
import net.rustmc.cloud.base.common.Rust;
import net.rustmc.cloud.base.common.events.natives.CloudNativeTabCompleteEvent;

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
public class ConsoleTabListener {

    public ConsoleTabListener(final CommandManager commandManager) {
        Rust.getInstance().getListenerBus().register(CloudNativeTabCompleteEvent.class, event -> {
            int pos = event.getWords().size();
            if(pos == 1){
                for (Command command : commandManager.getCommands()){
                    event.prompt(command.getName());
                    if (command.getAliases() != null)
                        for (String alias : command.getAliases())
                            event.prompt(alias);
                }
            }else {
                for (Command command : commandManager.getCommands()){
                    if(command.getName().equalsIgnoreCase(event.getWords().get(0))){
                        List<String> tab = command.onTab(pos-1);
                        if(tab != null) tab.forEach(event::prompt);
                        return;
                    }
                    for (String arg : command.getAliases()){
                        if(arg.equalsIgnoreCase(event.getWords().get(0))){
                            List<String> tab = command.onTab(pos-1);
                            if(tab != null) tab.forEach(event::prompt);
                        }
                    }
                }
            }
        });
    }
}
