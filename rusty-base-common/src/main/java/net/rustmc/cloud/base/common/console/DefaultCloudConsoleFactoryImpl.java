package net.rustmc.cloud.base.common.console;

import net.rustmc.cloud.base.console.ICloudConsole;
import net.rustmc.cloud.base.console.ICloudConsoleFactory;

import java.io.IOException;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
public class DefaultCloudConsoleFactoryImpl implements ICloudConsoleFactory {

    @Override
    public ICloudConsole newConsole() {
        try {
            return new DefaultCloudConsoleImpl();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
