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
    @ConfigurationProperty(name = "master.memory")
    private int memory = 12000;
    @ConfigurationProperty(name = "master.server.performance")
    private boolean unsafe = false;
    @ConfigurationProperty(name = "master.server.client-chain-connect")
    private boolean chainConnect = true;
    @ConfigurationProperty(name = "master.server.client-discard-time")
    private int clientTimeOutDiscard = 1000;

    public RustyMasterConfiguration() {
    }

}
