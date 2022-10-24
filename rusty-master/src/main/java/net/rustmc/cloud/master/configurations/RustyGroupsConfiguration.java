package net.rustmc.cloud.master.configurations;

import lombok.Getter;
import net.rustmc.cloud.api.objects.SimpleCloudGroup;
import net.rustmc.cloud.base.configuration.CloudConfiguration;
import net.rustmc.cloud.base.configuration.CloudConfigurationInfo;

import java.util.ArrayList;

/**
 * this file belongs to the rusty-cloud project.
 *
 * @author Alexander Jilge
 * @since 24.10.2022, Mo.
 */
@CloudConfigurationInfo(name = "groups")
@Getter
public final class RustyGroupsConfiguration implements CloudConfiguration {

    private final ArrayList<SimpleCloudGroup> groups = new ArrayList<>();

}
