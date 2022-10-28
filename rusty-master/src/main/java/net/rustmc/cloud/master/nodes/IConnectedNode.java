package net.rustmc.cloud.master.nodes;

import net.rustmc.cloud.api.objects.SimpleCloudNode;
import net.rustmc.cloud.master.groups.IOfflineGroup;
import net.rustmc.cloud.master.groups.IRemoteGroup;

import java.io.StringReader;
import java.util.List;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 25.10.2022
 */
public interface IConnectedNode {

    public int getTransmittedNodeKey();

    public String getNodeName();

    public SimpleCloudNode getLocalNodeObject();

    public int getRunningServices();

    public List<IRemoteGroup> getRunningGroups();

    public List<IOfflineGroup> getOfflineBootGroups();

    public int getFreeMemory();

    public void setOfflineGroups(String[] groups);

    public void setFreeMemory(int memory);

}
