package net.rustmc.cloud.base.common;

import lombok.Getter;
import net.rustmc.cloud.base.common.configuration.DefaultCloudConfigurationHandler;
import net.rustmc.cloud.base.common.console.DefaultCloudConsoleFactoryImpl;
import net.rustmc.cloud.base.common.events.CloudEventPerformerImpl;
import net.rustmc.cloud.base.common.events.CloudEventRegistryImpl;
import net.rustmc.cloud.base.common.threads.DefaultCloudThreadPool;
import net.rustmc.cloud.base.configuration.ICloudConfigurationHandler;
import net.rustmc.cloud.base.console.ICloudConsoleFactory;
import net.rustmc.cloud.base.events.ICloudEventPerformer;
import net.rustmc.cloud.base.events.ICloudEventRegistry;
import net.rustmc.cloud.base.threads.IThreadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
    private final ICloudConfigurationHandler configurationHandler = new DefaultCloudConfigurationHandler();
    private final ICloudEventRegistry eventRegistry = new CloudEventRegistryImpl();
    private final ICloudEventPerformer eventPerformer = new CloudEventPerformerImpl();
    private final ExecutorService asynchronousExecutor = Executors.newSingleThreadExecutor();
    private final String operatingSystem = System.getProperty("os.name");

}
