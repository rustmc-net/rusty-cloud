package net.rustmc.cloud.base.common;

import lombok.Getter;
import net.rustmc.cloud.base.common.communicate.CommunicateBaseChannelFactoryImpl;
import net.rustmc.cloud.base.common.communicate.SimpleCommunicatePacketPool;
import net.rustmc.cloud.base.common.configuration.DefaultCloudConfigurationHandler;
import net.rustmc.cloud.base.common.console.DefaultCloudConsoleFactoryImpl;
import net.rustmc.cloud.base.common.events.CloudEventPerformerImpl;
import net.rustmc.cloud.base.common.events.CloudListenerBusImpl;
import net.rustmc.cloud.base.common.threads.DefaultCloudThreadPool;
import net.rustmc.cloud.base.communicate.ICommunicateBaseChannelFactory;
import net.rustmc.cloud.base.communicate.ICommunicatePacketPool;
import net.rustmc.cloud.base.configuration.ICloudConfigurationHandler;
import net.rustmc.cloud.base.console.ICloudConsoleFactory;
import net.rustmc.cloud.base.events.ICloudEventPerformer;
import net.rustmc.cloud.base.events.ICloudListenerBus;
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
    private final ICloudListenerBus listenerBus = new CloudListenerBusImpl();
    private final ICloudEventPerformer eventPerformer = new CloudEventPerformerImpl();
    private final ICommunicatePacketPool communicatePacketPool = new SimpleCommunicatePacketPool();
    private final ICommunicateBaseChannelFactory channelFactory = new CommunicateBaseChannelFactoryImpl();
    private final ExecutorService asynchronousExecutor = Executors.newSingleThreadExecutor();
    private final String operatingSystem = System.getProperty("os.name");

}
