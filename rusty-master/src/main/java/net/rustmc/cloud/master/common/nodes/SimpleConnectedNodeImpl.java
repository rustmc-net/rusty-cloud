package net.rustmc.cloud.master.common.nodes;

import lombok.Setter;
import net.rustmc.cloud.api.objects.SimpleCloudNode;
import net.rustmc.cloud.master.groups.IOfflineGroup;
import net.rustmc.cloud.master.groups.IRemoteGroup;
import net.rustmc.cloud.master.nodes.IConnectedNode;

import java.util.List;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 25.10.2022
 */
@Setter
public class SimpleConnectedNodeImpl implements IConnectedNode {



    @Override
    public int getTransmittedNodeKey() {
        return 0;
    }

    @Override
    public String getNodeName() {
        return null;
    }

    @Override
    public SimpleCloudNode getLocalNodeObject() {
        return null;
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
        return null;
    }

}
