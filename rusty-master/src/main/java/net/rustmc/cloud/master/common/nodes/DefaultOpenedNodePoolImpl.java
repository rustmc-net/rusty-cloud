package net.rustmc.cloud.master.common.nodes;

import net.rustmc.cloud.api.objects.SimpleCloudGroup;
import net.rustmc.cloud.master.nodes.IConnectedNode;
import net.rustmc.cloud.master.nodes.IOpenedNodePool;

import java.util.List;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 25.10.2022
 */
public class DefaultOpenedNodePoolImpl implements IOpenedNodePool {

    @Override
    public List<IConnectedNode> getConnectedNodes() {
        return null;
    }

    @Override
    public IConnectedNode getSuitableNode(SimpleCloudGroup group) {
        return null;
    }

}
