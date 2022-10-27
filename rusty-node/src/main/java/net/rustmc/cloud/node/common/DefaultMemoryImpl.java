package net.rustmc.cloud.node.common;

import net.rustmc.cloud.node.memory.IMemoryMonitor;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 27.10.2022
 */
public class DefaultMemoryImpl implements IMemoryMonitor {

    private static int memoryFromRemote = -1;

    @Override
    public int getMaxMemoryFromRemote() {
        return memoryFromRemote;
    }

    @Override
    public int getFreeMemoryFromRemote() {
        return 0;
    }

    @Override
    public int getUsedMemory() {
        return 0;
    }

    public static void insert(final int memory) {
        memoryFromRemote = memory;
    }

}
