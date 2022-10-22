package net.rustmc.cloud.base.console;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
public interface ICloudConsoleGate {

    public ICloudConsoleGate warn();

    public ICloudConsoleGate info();

    public ICloudConsoleGate error();

}
