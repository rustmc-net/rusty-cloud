package net.rustmc.cloud.api.objects;

import lombok.Getter;

/**
 * this file belongs to the rusty-cloud project.
 *
 * @author Alexander Jilge
 * @since 24.10.2022, Mo.
 */
@Getter
public final class SimpleCloudGroup {

    private final String name;
    private final boolean proxy;
    private final int version;
    private final int maxPlayersPer;
    private final int percent = 80;
    private final int maxServers;
    private final int minServers = 0;
    private final int priority = 0;
    private final String permission = "null";

    public SimpleCloudGroup(String name, boolean proxy, int version, int maxPlayersPer, int maxServers) {
        this.name = name;
        this.proxy = proxy;
        this.version = version;
        this.maxPlayersPer = maxPlayersPer;
        this.maxServers = maxServers;
    }

}
