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
public final class SimpleCloudNode {

    private static final Random random = new Random();

    private final String name;
    private final String host;
    private final int maxMemory;
    private final int maxOnlineGroups;
    private final int nodeKey = random.nextInt();

    public SimpleCloudNode(String name, String host, int maxMemory) {
        this.name = name;
        this.host = host;
        this.maxMemory = maxMemory;
        this.maxOnlineGroups = -1;
    }

    @Override
    public String toString() {
        return "SimpleCloudNode{" +
                "name='" + name + '\'' +
                ", host='" + host + '\'' +
                ", maxMemory=" + maxMemory +
                ", maxOnlineGroups=" + maxOnlineGroups +
                '}';
    }
}
