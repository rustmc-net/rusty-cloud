package net.rustmc.cloud.master.nodes;

import java.io.File;
import java.util.Collection;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 13.11.2022
 */
public interface IOfflineNodeTerminal {

    public IOfflineNode registerCloudNodeConfiguration(File file);

    public IOfflineNode getOfflineNodeByName(String name);

    public IOfflineNode getOfflineNodeByNodeKey(int nodeKey);

    public IOfflineNode newOfflineNode(String name, int maxGroups);

    public Collection<IOfflineNode> getOfflineNodes();

}
