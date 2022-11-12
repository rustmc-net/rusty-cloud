package net.rustmc.cloud.master.configurations;

import net.rustmc.cloud.base.configuration.CloudConfiguration;
import net.rustmc.cloud.base.configuration.CloudConfigurationInfo;
import net.rustmc.cloud.base.configuration.ConfigurationProperty;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 12.11.2022
 */
@CloudConfigurationInfo(name = "base")
public class CloudBaseConfiguration implements CloudConfiguration {

    @ConfigurationProperty(name = "cloud.channel.host")
    private String host = "127.0.0.1";
    @ConfigurationProperty(name = "cloud.channel.port")
    private int port = 2000;

}
