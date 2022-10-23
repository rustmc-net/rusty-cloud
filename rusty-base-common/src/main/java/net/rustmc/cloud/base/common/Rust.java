package net.rustmc.cloud.base.common;

import lombok.Getter;
import net.rustmc.cloud.base.common.console.DefaultCloudConsoleFactoryImpl;
import net.rustmc.cloud.base.common.threads.DefaultCloudThreadPool;
import net.rustmc.cloud.base.console.ICloudConsoleFactory;
import net.rustmc.cloud.base.threads.IThreadPool;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
@Getter
public final class Rust {

    @Getter
    private static final Rust instance = new Rust();

    private final ICloudConsoleFactory consoleFactory = new DefaultCloudConsoleFactoryImpl();
    private final IThreadPool threadPool = new DefaultCloudThreadPool();
    private final String operatingSystem = System.getProperty("os.name");

}
