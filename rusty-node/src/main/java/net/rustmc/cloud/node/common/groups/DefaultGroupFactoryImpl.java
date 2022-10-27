package net.rustmc.cloud.node.common.groups;

import net.rustmc.cloud.base.packets.input.groups.PacketInGroupInfoRequest;
import net.rustmc.cloud.node.RustCloud;
import net.rustmc.cloud.node.groups.IGroupFactory;
import net.rustmc.cloud.node.groups.IRemoteOnlineGroup;
import net.rustmc.cloud.node.groups.IStatedCacheGroup;
import net.rustmc.cloud.node.handlers.PacketOutGroupInfoRequestHandler;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 27.10.2022
 */
public class DefaultGroupFactoryImpl implements IGroupFactory {

    private final LinkedList<IStatedCacheGroup> statedCacheGroups = new LinkedList<>();
    private final LinkedList<IRemoteOnlineGroup> remoteOnlineGroups = new LinkedList<>();

    @Override
    public Collection<IStatedCacheGroup> getStayedCacheGroups() {
        return this.statedCacheGroups;
    }

    @Override
    public Collection<IRemoteOnlineGroup> getRemoteOnlineGroups() {
        return this.remoteOnlineGroups;
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void loadStayedCacheGroups() {
        if (RustCloud.getCloud().getStorageFile().listFiles() != null) {
            for (File file : RustCloud.getCloud().getStorageFile().listFiles()) {
                if (file.isDirectory()) {
                    this.statedCacheGroups.addLast(new SimpleLocalStayedCacheGroupImpl(file));
                }
            }
        }
    }

    @Override
    public void logIn(IRemoteOnlineGroup localOnlineGroup) {
        this.remoteOnlineGroups.addLast(localOnlineGroup);
    }

    @Override
    public IRemoteOnlineGroup request(String name) {
        RustCloud.getCloud().getCommunicateBaseChannel().dispatch(new PacketInGroupInfoRequest(name));
        while (!Thread.currentThread().isInterrupted()) {
            if (PacketOutGroupInfoRequestHandler.contains(name)) {
                return PacketOutGroupInfoRequestHandler.request(name);
            }
        }
        throw new UnsupportedOperationException("The request of " + name + " could not resolved!");
    }
}
