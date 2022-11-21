package net.rustmc.cloud.master.nodes;

import net.rustmc.cloud.base.communicate.CommunicatePacket;
import net.rustmc.cloud.base.communicate.ICommunicateChannel;

import java.io.File;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 21.11.2022
 */
public interface IOnlineNode extends IOfflineNode {

    public IOnlineNode request(NodeRequest nodeRequest);

    public ICommunicateChannel getNodeCommunicateChannel();

    public void close();

    public IOnlineNode dispatch(File file);

    public IOnlineNode dispatch(CommunicatePacket<?> communicatePacket);

    public Object get(NodeRequest nodeRequest);

    public Object getWithRequestBefore(NodeRequest nodeRequest);

    public IOfflineNode offline();

    public void store(String k, Object v);

    public static enum NodeRequest {
        REMOTE_GROUPS,
        REMOTE_MEMORY,
        REMOTE_OPERATING_SYSTEM
    }

}
