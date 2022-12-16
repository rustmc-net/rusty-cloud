package net.rustmc.cloud.node.commons.groups;

import net.rustmc.cloud.node.groups.IOnlineGroup;
import net.rustmc.cloud.node.groups.IOnlineGroupPool;

import java.util.LinkedList;
import java.util.List;

public class OnlineGroupPoolImpl implements IOnlineGroupPool {

    private final LinkedList<IOnlineGroup> groups = new LinkedList<>();

    @Override
    public List<IOnlineGroup> getOnlineGroups() {
        return this.groups;
    }

    @Override
    public void register(IOnlineGroup onlineGroup) {
        this.groups.addLast(onlineGroup);
    }

}
