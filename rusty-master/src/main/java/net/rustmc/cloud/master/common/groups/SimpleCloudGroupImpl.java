package net.rustmc.cloud.master.common.groups;

import net.rustmc.cloud.base.objects.SimpleCloudGroup;
import net.rustmc.cloud.master.groups.ICloudGroup;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 21.11.2022
 */
public class SimpleCloudGroupImpl implements ICloudGroup {

    private final SimpleCloudGroup cloudGroup;

    public SimpleCloudGroupImpl(SimpleCloudGroup cloudGroup) {
        this.cloudGroup = cloudGroup;
    }

    @Override
    public SimpleCloudGroup getObject() {
        return this.cloudGroup;
    }

}
