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

    public <T extends CloudConfiguration> T open(final String name, final URI uri, Class<? extends T> tClass);

    public <T extends CloudConfiguration> T open(final String name, final URI uri, T object);

    public void close();

    public void close(String name);

}
