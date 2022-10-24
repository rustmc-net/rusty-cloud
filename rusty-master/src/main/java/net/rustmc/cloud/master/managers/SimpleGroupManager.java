package net.rustmc.cloud.master.managers;

import net.rustmc.cloud.api.objects.SimpleCloudGroup;
import net.rustmc.cloud.master.RustCloud;

/**
 * this file belongs to the rusty-cloud project.
 *
 * @author Alexander Jilge
 * @since 24.10.2022, Mo.
 */
public final class SimpleGroupManager {

    public void register(final SimpleCloudGroup cloudGroup) {
        RustCloud.getCloud().getGroupsConfiguration().getGroups().add(cloudGroup);
    }

}