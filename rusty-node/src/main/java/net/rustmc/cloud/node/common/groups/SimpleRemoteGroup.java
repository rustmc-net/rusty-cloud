package net.rustmc.cloud.node.common.groups;

import net.rustmc.cloud.node.groups.IRemoteGroup;

import java.io.File;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 28.10.2022
 */
public class SimpleRemoteGroup implements IRemoteGroup {

    private final String name;
    private final int version;
    private final int maxPlayers;
    private final int maxMemory;
    private final int maxServers;
    private final int minServers;

    public SimpleRemoteGroup(String name, int version, int maxPlayers, int maxMemory, int maxServers, int minServers) {
        this.name = name;
        this.version = version;
        this.maxPlayers = maxPlayers;
        this.maxMemory = maxMemory;
        this.maxServers = maxServers;
        this.minServers = minServers;
    }

    @Override
    public String getGroupName() {
        return this.name;
    }

    @Override
    public int getVersion() {
        return this.version;
    }

    @Override
    public int getMaxPlayers() {
        return this.maxPlayers;
    }

    @Override
    public int getMaxMemory() {
        return this.maxMemory;
    }

    @Override
    public int getMaxServers() {
        return this.maxServers;
    }

    @Override
    public int getMinServers() {
        return this.minServers;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Override
    public File getDirectory() {
        final var out = new File("storages//" + this.getGroupName());
        if (!out.exists()) out.mkdir();
        return out;
    }
}
