package net.rustmc.cloud.node.configurations;

import lombok.Getter;
import net.rustmc.cloud.base.configuration.CloudConfiguration;
import net.rustmc.cloud.base.configuration.CloudConfigurationInfo;
import net.rustmc.cloud.base.configuration.ConfigurationProperty;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 26.10.2022
 */
@SuppressWarnings("FieldMayBeFinal")
@Getter
@CloudConfigurationInfo(name = "node")
public final class RustyNodeConfiguration implements CloudConfiguration {

    @ConfigurationProperty(name = "master.host")
    private String host = "127.0.0.1";
    @ConfigurationProperty(name = "master.port")
    private int port = 187;
    @ConfigurationProperty(name = "client.terminate-after-failure")
    private boolean retry = false;

}
