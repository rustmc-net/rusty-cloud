package net.rustmc.cloud.base.common.console;

import net.rustmc.cloud.base.console.ICloudConsole;
import net.rustmc.cloud.base.console.ICloudConsoleFactory;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
public class DefaultCloudConsoleFactoryImpl implements ICloudConsoleFactory {

    @Override
    public ICloudConsole newConsole() {
        return new DefaultCloudConsoleImpl();
    }

}
