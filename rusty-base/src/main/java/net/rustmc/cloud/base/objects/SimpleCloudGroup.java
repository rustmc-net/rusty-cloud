package net.rustmc.cloud.base.objects;

import lombok.Getter;

import java.util.Random;

/**
 * this file belongs to the rusty-cloud project.
 *
 * @author Alexander Jilge
 * @since 24.10.2022, Mo.
 */
@Getter
public final class SimpleCloudGroup {

    private static final Random random = new Random();

    private final String name;
    private final boolean proxy;
    private final int version;
    private final int maxPlayersPer;
    private final int percent = 80;
    private final int maxServers;
    private final int minServers = 0;
    private final int priority = 0;
    private final String permission = "null";
    private final int memory;
    private String allocatedNode = "null";

    public SimpleCloudGroup(String name, boolean proxy, int version, int maxPlayersPer, int maxServers, int memory) {
        this.name = name;
        this.proxy = proxy;
        this.version = version;
        this.maxPlayersPer = maxPlayersPer;
        this.maxServers = maxServers;
        this.memory = memory;
    }

    @Override
    public String toString() {
        return "SimpleCloudGroup{" +
                "name='" + name + '\'' +
                ", proxy=" + proxy +
                ", version=" + version +
                ", maxPlayersPer=" + maxPlayersPer +
                ", percent=" + percent +
                ", maxServers=" + maxServers +
                ", minServers=" + minServers +
                ", priority=" + priority +
                ", permission='" + permission + '\'' +
                ", memory=" + memory +
                '}';
    }

    public void setAllocatedNode(String allocatedNode) {
        this.allocatedNode = allocatedNode;
    }

}
