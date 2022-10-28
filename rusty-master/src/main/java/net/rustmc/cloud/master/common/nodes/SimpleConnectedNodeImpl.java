package net.rustmc.cloud.master.common.nodes;

import lombok.Setter;
import net.rustmc.cloud.api.objects.SimpleCloudNode;
import net.rustmc.cloud.master.RustCloud;
import net.rustmc.cloud.master.groups.IOfflineGroup;
import net.rustmc.cloud.master.groups.IRemoteGroup;
import net.rustmc.cloud.master.nodes.IConnectedNode;

import java.util.ArrayList;
import java.util.List;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 25.10.2022
 */
@Setter
public class SimpleConnectedNodeImpl implements IConnectedNode {

    private final List<IOfflineGroup> offlineGroups = new ArrayList<>();
    private final int nodeKey;
    private int freeMemory = -1;

    public SimpleConnectedNodeImpl(int nodeKey) {
        this.nodeKey = nodeKey;
    }

    @Override
    public int getTransmittedNodeKey() {
        return 0;
    }

    @Override
    public String getNodeName() {
        return RustCloud.getCloud().getNodeManager().getNameOfNodeKey(this.nodeKey);
    }

    @Override
    public SimpleCloudNode getLocalNodeObject() {
        return RustCloud.getCloud().getNodeManager().getNodeByNodeKey(this.nodeKey);
    }

    @Override
    public int getRunningServices() {
        return 0;
    }

    @Override
    public List<IRemoteGroup> getRunningGroups() {
        return null;
    }

    @Override
    public List<IOfflineGroup> getOfflineBootGroups() {
        return this.offlineGroups;
    }

    @Override
    public int getFreeMemory() {
        return this.freeMemory;
    }

    @Override
    public void setOfflineGroups(String[] groups) {
        this.offlineGroups.clear();
        for (String group : groups) {
            this.offlineGroups.add(RustCloud.getCloud()
                    .getGroupFactory()
                    .newOfflineGroup(group));
        }
    }

    @Override
    public void setFreeMemory(int memory) {
        this.freeMemory = memory;
    }

}
