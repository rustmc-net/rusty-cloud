package net.rustmc.cloud.node.groups;

import java.util.Collection;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 27.10.2022
 */
public interface IGroupFactory {

    public Collection<IStatedCacheGroup> getStayedCacheGroups();

    public Collection<IRemoteGroup> getRemoteOnlineGroups();

    public void loadStayedCacheGroups();

    public void logIn(IRemoteGroup localOnlineGroup);

    public IRemoteGroup request(final String name);

    public void requestAsynchronousForStayed();

    public Collection<IRemoteGroup> getRemoteGroups();

    public IRemoteGroup of(final String name, final int memory, final int maxServer, final int minServers, final int version, final int maxPlayers);

}
