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

    public Collection<IRemoteOnlineGroup> getRemoteOnlineGroups();

    public void loadStayedCacheGroups();

    public void logIn(IRemoteOnlineGroup localOnlineGroup);

    public IRemoteOnlineGroup request(final String name);

}
