package net.rustmc.cloud.master.common.nodes;

import net.rustmc.cloud.api.objects.SimpleCloudGroup;
import net.rustmc.cloud.master.nodes.IConnectedNode;
import net.rustmc.cloud.master.nodes.IOpenedNodePool;

import java.util.LinkedList;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 25.10.2022
 */
public class DefaultOpenedNodePoolImpl implements IOpenedNodePool {

    private final LinkedList<IConnectedNode> nodes = new LinkedList<>();

    @Override
    public LinkedList<IConnectedNode> getConnectedNodes() {
        return this.nodes;
    }

    @Override
    public IConnectedNode getSuitableNode(SimpleCloudGroup group) {
        return null;
    }

    @Override
    public IConnectedNode getByNodeKey(int nodeKey) {
        for (IConnectedNode node : this.nodes) {
            if (node.getTransmittedNodeKey() == nodeKey) return node;
        }
        return null;
    }

}
