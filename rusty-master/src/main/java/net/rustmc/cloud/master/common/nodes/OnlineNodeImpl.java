package net.rustmc.cloud.master.common.nodes;

import io.netty.handler.stream.ChunkedFile;
import net.rustmc.cloud.base.communicate.CommunicatePacket;
import net.rustmc.cloud.base.communicate.ICommunicateChannel;
import net.rustmc.cloud.master.RustCloud;
import net.rustmc.cloud.master.configurations.CloudNodeConfiguration;
import net.rustmc.cloud.master.groups.ICloudGroup;
import net.rustmc.cloud.master.nodes.IOfflineNode;
import net.rustmc.cloud.master.nodes.IOnlineNode;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 21.11.2022
 */
public class OnlineNodeImpl implements IOnlineNode {

    private final IOfflineNode offlineNode;
    private final ICommunicateChannel channel;
    private final HashMap<String, Object> storage = new HashMap<>();

    public OnlineNodeImpl(IOfflineNode offlineNode, ICommunicateChannel channel) {
        this.offlineNode = offlineNode;
        this.channel = channel;
    }

    @Override
    public IOnlineNode request(NodeRequest nodeRequest) {
        return null;
    }

    @Override
    public ICommunicateChannel getNodeCommunicateChannel() {
        return this.channel;
    }

    @Override
    public void close() {
        RustCloud.getCloud().getOnlineNodeTerminal().remove(this.offlineNode.configuration().getName());
        this.channel.decline();
    }

    @Override
    public IOnlineNode dispatch(File file) {
        RustCloud.getCloud().getCommunicateChannel().dispatch(file, this.channel.getUniqueID());
        return this;
    }

    @Override
    public IOnlineNode dispatch(CommunicatePacket<?> communicatePacket) {
        RustCloud.getCloud().getCommunicateChannel().dispatch(communicatePacket, this.channel.getUniqueID());
        return this;
    }

    @Override
    public Object get(NodeRequest nodeRequest) {
        return this.storage.get(nodeRequest.name());
    }

    @Override
    public Object getWithRequestBefore(NodeRequest nodeRequest) {
        return null;
    }

    @Override
    public IOfflineNode offline() {
        return this.offlineNode;
    }

    @Override
    public void store(String k, Object v) {
        this.storage.put(k, v);
    }

    @Override
    public File file() {
        return this.offlineNode.file();
    }

    @Override
    public CloudNodeConfiguration configuration() {
        return this.offlineNode.configuration();
    }

    @Override
    public List<ICloudGroup> getAllocatedGroups() {
        return this.offlineNode.getAllocatedGroups();
    }

}
