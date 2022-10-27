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

    public Collection<ILocalOnlineGroup> getLocalOnlineGroups();

    public void loadStayedCacheGroups();

    public void logIn(ILocalOnlineGroup localOnlineGroup);

}
