package net.rustmc.cloud.node.common;

import net.rustmc.cloud.node.RustCloud;
import net.rustmc.cloud.node.groups.IRemoteGroup;
import net.rustmc.cloud.node.memory.IMemoryMonitor;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 27.10.2022
 */
public class DefaultMemoryImpl implements IMemoryMonitor {

    private static int memoryFromRemote = -1;

    private int memory = -1;

    @Override
    public int getMaxMemoryFromRemote() {
        return memoryFromRemote;
    }

    @Override
    public int getFreeMemoryFromRemote() {
        return this.getMaxMemoryFromRemote()-this.getUsedMemory();
    }

    @Override
    public int getUsedMemory() {
        return memory;
    }

    @Override
    public void update() {
        int i = 0;
        for (IRemoteGroup remoteGroup : RustCloud.getCloud().getGroupFactory().getRemoteGroups()) {
            if (remoteGroup.getMaxServers() == 1)
                i = i + remoteGroup.getMaxMemory();
            else if (remoteGroup.getMaxServers() == remoteGroup.getMinServers())
                i = i + remoteGroup.getMaxMemory() * remoteGroup.getMaxServers();
            else if (remoteGroup.getMaxServers() - remoteGroup.getMinServers() >= 3)
                i = i + remoteGroup.getMaxMemory() * (remoteGroup.getMinServers() + 2);
            else
                i = i + remoteGroup.getMaxMemory();
        }
        this.memory = i;
    }

    public static void insert(final int memory) {
        memoryFromRemote = memory;
    }

}
