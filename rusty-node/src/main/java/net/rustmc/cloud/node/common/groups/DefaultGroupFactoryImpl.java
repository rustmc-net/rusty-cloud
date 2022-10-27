package net.rustmc.cloud.node.common.groups;

import net.rustmc.cloud.base.common.Rust;
import net.rustmc.cloud.base.packets.input.groups.PacketInGroupInfoRequest;
import net.rustmc.cloud.node.RustCloud;
import net.rustmc.cloud.node.groups.IGroupFactory;
import net.rustmc.cloud.node.groups.IRemoteGroup;
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
    private final LinkedList<IRemoteGroup> remoteOnlineGroups = new LinkedList<>();
    private final LinkedList<IRemoteGroup> remoteGroups = new LinkedList<>();

    @Override
    public Collection<IStatedCacheGroup> getStayedCacheGroups() {
        return this.statedCacheGroups;
    }

    @Override
    public Collection<IRemoteGroup> getRemoteOnlineGroups() {
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
    public void logIn(IRemoteGroup localOnlineGroup) {
        this.remoteOnlineGroups.addLast(localOnlineGroup);
    }

    @Override
    public IRemoteGroup request(String name) {
        RustCloud.getCloud().getCommunicateBaseChannel().dispatch(new PacketInGroupInfoRequest(name));
        while (!Thread.currentThread().isInterrupted()) {
            if (PacketOutGroupInfoRequestHandler.contains(name)) {
                return PacketOutGroupInfoRequestHandler.request(name);
            }
        }
        throw new UnsupportedOperationException("The request of " + name + " could not resolved!");
    }

    @Override
    public void requestAsynchronousForStayed() {
        Rust.getInstance().getAsynchronousExecutor().submit(new Runnable() {
            @Override
            public void run() {
                for (IStatedCacheGroup group : DefaultGroupFactoryImpl.this.getStayedCacheGroups()) {
                    DefaultGroupFactoryImpl
                            .this
                            .remoteGroups
                            .add(DefaultGroupFactoryImpl
                                            .this
                                            .request(group.getGroupName())
                            );
                }
            }
        });
    }

    @Override
    public Collection<IRemoteGroup> getRemoteGroups() {
        return this.remoteGroups;
    }

}
