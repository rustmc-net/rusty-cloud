package net.rustmc.cloud.node.groups;

import java.io.File;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 27.10.2022
 */
public interface IRemoteGroup {

    public String getGroupName();

    public int getVersion();

    public int getMaxPlayers();

    public int getMaxMemory();

    public int getMaxServers();

    public int getMinServers();

    public File getDirectory();

}
