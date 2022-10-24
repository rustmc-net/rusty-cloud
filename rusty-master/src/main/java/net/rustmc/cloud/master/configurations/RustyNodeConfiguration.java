package net.rustmc.cloud.master.configurations;

import lombok.Getter;
import net.rustmc.cloud.api.objects.SimpleCloudNode;
import net.rustmc.cloud.base.configuration.CloudConfiguration;

/**
 * this file belongs to the rusty-cloud project.
 *
 * @author Alexander Jilge
 * @since 24.10.2022, Mo.
 */
@SuppressWarnings("ClassCanBeRecord")
@Getter
public final class RustyNodeConfiguration implements CloudConfiguration {

    private final SimpleCloudNode node;

    public RustyNodeConfiguration(SimpleCloudNode node) {
        this.node = node;
    }

}
