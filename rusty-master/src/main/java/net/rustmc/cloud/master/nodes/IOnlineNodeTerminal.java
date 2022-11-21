package net.rustmc.cloud.master.nodes;

import net.rustmc.cloud.base.communicate.ICommunicateChannel;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 21.11.2022
 */
public interface IOnlineNodeTerminal {

    public void open(IOfflineNode offlineNode, ICommunicateChannel communicateChannel);

    public IOnlineNode getByName(String name);

    public IOnlineNode getByNodeKey(int nodeKey);

    public IOnlineNode getByUniqueID(String uniqueID);

    public void remove(String name);

    public int size();

}
