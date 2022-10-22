package net.rustmc.cloud.base.console;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
public interface ICloudConsole {

    public ICloudConsole send(final ICloudConsoleGate gate);

    public void clear();

}
