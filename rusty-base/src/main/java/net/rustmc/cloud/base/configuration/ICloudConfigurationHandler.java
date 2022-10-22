package net.rustmc.cloud.base.configuration;

import java.net.URI;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
public interface ICloudConfigurationHandler {

    public <T extends CloudConfiguration> T open(final URI uri, Class<? extends T> tClass);

    public void close();

    public void close(Class<? extends CloudConfiguration> tClass);

}
