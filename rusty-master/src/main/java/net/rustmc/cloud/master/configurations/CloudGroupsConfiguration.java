package net.rustmc.cloud.master.configurations;

import net.rustmc.cloud.base.configuration.CloudConfiguration;
import net.rustmc.cloud.base.configuration.CloudConfigurationInfo;
import net.rustmc.cloud.base.objects.SimpleCloudGroup;

import java.util.LinkedList;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 13.11.2022
 */
@SuppressWarnings("FieldMayBeFinal")
@CloudConfigurationInfo(name = "groups")
public class CloudGroupsConfiguration implements CloudConfiguration {

    private LinkedList<SimpleCloudGroup> groups = new LinkedList<>();

    public LinkedList<SimpleCloudGroup> getGroups() {
        return groups;
    }

}
