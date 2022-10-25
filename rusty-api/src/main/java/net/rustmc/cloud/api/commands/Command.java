package net.rustmc.cloud.api.commands;

import lombok.Getter;

import java.util.List;

/**
 * This file is a JavaDoc!
 * Created: 10/23/2022
 *
 * @author RedCrew
 * Discord: RedCrew#0100
 * Website: https://redcrewtv.de/
 */
@Getter
public abstract class Command {

    private final String name;
    private final String[] aliases;
    private final String description;

    protected Command(String name) {
        this(name, null, null);
    }

    protected Command(String name, String description) {
        this(name, description, null);
    }

    protected Command(String name, String... aliases){
        this(name, null, aliases);
    }

    protected Command(String name, String description, String... aliases){
        this.name = name;
        this.aliases = aliases;
        this.description = description;
    }

    public abstract void execute(String[] args);

    public abstract List<String> onTab(int pos, String line);

}
