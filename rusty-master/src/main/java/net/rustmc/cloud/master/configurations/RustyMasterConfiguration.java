package net.rustmc.cloud.master.configurations;

import lombok.Getter;
import net.rustmc.cloud.base.configuration.CloudConfiguration;
import net.rustmc.cloud.base.configuration.CloudConfigurationInfo;
import net.rustmc.cloud.base.configuration.ConfigurationProperty;

/**
 * this file belongs to the rusty-cloud project.
 *
 * @author Alexander Jilge
 * @since 24.10.2022, Mo.
 */
@CloudConfigurationInfo(name = "master")
@SuppressWarnings("FieldMayBeFinal")
@Getter
public final class RustyMasterConfiguration implements CloudConfiguration {

    @ConfigurationProperty(name = "master.port")
    private int port = 2222;
    @ConfigurationProperty(name = "master.auto-updater.config")
    private boolean autoConfigurationUpdater = false;

    public RustyMasterConfiguration() {
    }

}
