package net.rustmc.cloud.api.commands;

import lombok.Getter;

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
public class CommandManager {

    @Getter
    private final List<Command> commands = new LinkedList<>();

    public void register(Command command) {
        if(!commands.contains(command)){
            commands.add(command);
        }
    }

    public void unregister(Command command) {
        commands.remove(command);
    }

    public Command getCommand(String name){
        for (Command command : commands) {
            if(command.getName().equalsIgnoreCase(name)){
                return command;
            }
        }
        return null;
    }

}
