package net.rustmc.cloud.node.commons.groups;

import net.rustmc.cloud.base.objects.SimpleCloudGroup;
import net.rustmc.cloud.node.groups.IOnlineGroup;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 21.11.2022
 */
public abstract class Group implements IOnlineGroup {

    protected final SimpleCloudGroup cloudGroup;

    public Group(SimpleCloudGroup cloudGroup) {
        this.cloudGroup = cloudGroup;
    }

    @Override
    public abstract void start();

    @Override
    public abstract void shutdown();

    public abstract String getPath();

    @Override
    public SimpleCloudGroup getObject() {
        return this.cloudGroup;
    }

}
