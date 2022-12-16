package net.rustmc.cloud.node.groups;

import java.util.List;

public interface IOnlineGroupPool {

    public List<IOnlineGroup> getOnlineGroups();

    public void register(IOnlineGroup onlineGroup);

}
