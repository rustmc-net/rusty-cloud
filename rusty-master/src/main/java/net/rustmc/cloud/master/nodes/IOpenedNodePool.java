package net.rustmc.cloud.master.nodes;

import net.rustmc.cloud.api.objects.SimpleCloudGroup;

import java.util.List;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 25.10.2022
 */
public interface IOpenedNodePool {

    public List<IConnectedNode> getConnectedNodes();

    public IConnectedNode getSuitableNode(final SimpleCloudGroup group);

}
