package net.rustmc.cloud.base.console;

import java.util.function.Consumer;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
public interface ICloudConsole {

    public ICloudConsole send(final String output, final Output level);

    public ICloudConsole send(final String output);

    public void clear();

    public ICloudConsole run();

    public void close();

    public ICloudConsole push(final Consumer<String> handler);

    public static enum Output {

        INFO,
        WARN,
        ERROR

    }

}
