package net.rustmc.cloud.node.groups;

import net.rustmc.cloud.base.objects.SimpleCloudGroup;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 21.11.2022
 */
public interface IOnlineGroup {

    public void start();

    public void shutdown();

    public SimpleCloudGroup getObject();

}
