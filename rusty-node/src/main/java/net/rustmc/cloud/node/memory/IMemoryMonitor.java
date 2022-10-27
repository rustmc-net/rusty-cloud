package net.rustmc.cloud.node.memory;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 27.10.2022
 */
public interface IMemoryMonitor {

    public int getMaxMemoryFromRemote();

    public int getFreeMemoryFromRemote();

    public int getUsedMemory();

}
