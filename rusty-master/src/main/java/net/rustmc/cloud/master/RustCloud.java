package net.rustmc.cloud.master;

import lombok.Getter;
import lombok.SneakyThrows;
import net.rustmc.cloud.api.commands.CommandManager;
import net.rustmc.cloud.api.commands.listeners.ConsoleInputListener;
import net.rustmc.cloud.api.commands.listeners.ConsoleTabListener;
import net.rustmc.cloud.base.common.Rust;
import net.rustmc.cloud.base.common.packets.ConstantPacketRegistryCluster;
import net.rustmc.cloud.base.communicate.ConnectFailException;
import net.rustmc.cloud.base.communicate.IChannelBootstrap;
import net.rustmc.cloud.base.communicate.ICommunicateBaseChannel;
import net.rustmc.cloud.base.console.ICloudConsole;
import net.rustmc.cloud.base.util.FileHelper;
import net.rustmc.cloud.master.commands.CloseCommand;
import net.rustmc.cloud.master.commands.ProduceCommand;
import net.rustmc.cloud.master.common.channels.ChannelFlowImpl;
import net.rustmc.cloud.master.common.groups.DefaultGroupFactoryImpl;
import net.rustmc.cloud.master.common.groups.DefaultGroupRequestQueueImpl;
import net.rustmc.cloud.master.common.groups.DefaultOfflineGroupPoolImpl;
import net.rustmc.cloud.master.common.groups.DefaultRemoteGroupPoolImpl;
import net.rustmc.cloud.master.common.modules.DefaultInstanceLoaderImpl;
import net.rustmc.cloud.master.common.nodes.DefaultOpenedNodePoolImpl;
import net.rustmc.cloud.master.configurations.RustyGroupsConfiguration;
import net.rustmc.cloud.master.configurations.RustyMasterConfiguration;
import net.rustmc.cloud.master.configurations.RustyNodeConfiguration;
import net.rustmc.cloud.master.groups.IGroupFactory;
import net.rustmc.cloud.master.groups.IGroupRequestQueue;
import net.rustmc.cloud.master.groups.IOfflineGroupPool;
import net.rustmc.cloud.master.groups.IRemoteGroupPool;
import net.rustmc.cloud.master.handlers.PacketInHandshakeHandler;
import net.rustmc.cloud.master.handlers.PacketInNodeMemoryHandler;
import net.rustmc.cloud.master.handlers.channel.ChannelConnectHandler;
import net.rustmc.cloud.master.handlers.channel.IChannelFlow;
import net.rustmc.cloud.master.managers.SimpleGroupManager;
import net.rustmc.cloud.master.managers.SimpleNodeManager;
import net.rustmc.cloud.master.modules.IInstanceLoader;
import net.rustmc.cloud.master.nodes.IOpenedNodePool;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
@SuppressWarnings("SpellCheckingInspection")
@Getter
public final class RustCloud {

    @Getter
    private static RustCloud cloud;

    private final ICloudConsole cloudConsole;
    private final IChannelBootstrap bootstrap = Rust.getInstance().getChannelFactory().bootstrap();
    private final RustyMasterConfiguration configuration = Rust.getInstance().getConfigurationHandler().open(new File("master.json").toURI(), RustyMasterConfiguration.class);
    private final RustyGroupsConfiguration groupsConfiguration = Rust.getInstance().getConfigurationHandler().open(new File("groups.json").toURI(), RustyGroupsConfiguration.class);
    private final ICommunicateBaseChannel communicateBaseChannel;
    private final CommandManager commandManager = new CommandManager();
    private final File nodeFile = new File("nodes");
    private final File moduleFile = new File("modules");
    private final File groupFile = new File("groups");
    private final File tempFile = new File("temp");
    private final ArrayList<RustyNodeConfiguration> nodeConfigurations = new ArrayList<>();
    private final SimpleGroupManager groupManager = new SimpleGroupManager();
    private final SimpleNodeManager nodeManager = new SimpleNodeManager();
    private final IInstanceLoader instanceLoader = new DefaultInstanceLoaderImpl();
    private final IOpenedNodePool openedNodePool = new DefaultOpenedNodePoolImpl();
    private final IRemoteGroupPool remoteGroupPool = new DefaultRemoteGroupPoolImpl();
    private final IOfflineGroupPool offlineGroupPool = new DefaultOfflineGroupPoolImpl();
    private final IChannelFlow channelFlow = new ChannelFlowImpl();
    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    private final IGroupRequestQueue groupRequestQueue = new DefaultGroupRequestQueueImpl();
    private final IGroupFactory groupFactory = new DefaultGroupFactoryImpl();

    public RustCloud() throws MalformedURLException, URISyntaxException {

        this.cloudConsole = Rust.getInstance().getConsoleFactory().newConsole();
        this.cloudConsole.print();

        Thread.setDefaultUncaughtExceptionHandler((t, e) -> {
            RustCloud.this
                    .getCloudConsole()
                    .send(
                            "caught an unexpected error (§c" + e.getClass().getSimpleName() + "§r).",
                            ICloudConsole.Output.ERROR
                    );

            RustCloud.this
                    .getCloudConsole()
                    .send(
                            e.getLocalizedMessage(),
                            ICloudConsole.Output.ERROR
                    );

            for (StackTraceElement element : e.getStackTrace()) {
                if (element.getModuleName() != null) {
                    this.getCloudConsole().send(
                            "at " +
                                    element.getModuleName() +
                                    "/" + element.getClass().getPackageName() +
                                    "." + element.getClassName() + "." +
                                    element.getMethodName() + "(" +
                                    element.getClass().getSimpleName() + ":" +
                                    element.getLineNumber() +
                                    ")"
                            , ICloudConsole.Output.ERROR);
                } else {
                    this.getCloudConsole().send(
                            "at " +
                                    element.getClass().getPackageName() +
                                    "." + element.getClassName() + "." +
                                    element.getMethodName() + "(" +
                                    element.getClass().getSimpleName() + ":" +
                                    element.getLineNumber() +
                                    ")"
                            , ICloudConsole.Output.ERROR);
                }
            }

        });

        this.cloudConsole.run();

        new ConsoleInputListener(this.getCommandManager());
        new ConsoleTabListener(this.getCommandManager());

        this.getCommandManager().register(new CloseCommand());
        this.getCommandManager().register(new ProduceCommand());

        FileHelper.create(nodeFile);
        FileHelper.create(moduleFile);
        FileHelper.create(tempFile);
        FileHelper.create(groupFile);
        for (final File tempNodeFile : Arrays
                .stream(Objects.requireNonNull(this.nodeFile.listFiles()))
                .filter(file -> file.getName().endsWith(".json"))
                .toList()) {
            final var nodeConfiguraion = Rust.getInstance().getConfigurationHandler().open(
                            tempNodeFile
                                    .getName()
                                    .replace(".json", ""),
                            tempNodeFile.toURI(),
                            RustyNodeConfiguration.class
                    );
            this.nodeConfigurations.add(nodeConfiguraion);
            this.cloudConsole.send("Waiting for node: §e" + nodeConfiguraion.getNode().getName() + " §ron: §e" + nodeConfiguraion.getNode().getHost() + "§r.");
        }

        try {
            this.communicateBaseChannel = this.bootstrap.port(this.configuration.getPort()).open();
        } catch (ConnectFailException e) {
            throw new RuntimeException(e);
        }

    }

    @SuppressWarnings("ConstantConditions")
    public void onBoot() {

        this.offlineGroupPool.load();
        this.groupRequestQueue.load(this.offlineGroupPool);

        new ChannelConnectHandler();
        new PacketInHandshakeHandler();
        new PacketInNodeMemoryHandler();

        new ConstantPacketRegistryCluster();

        this.getCloudConsole().send("The cloud started on port §a" + this.configuration.getPort() + "§r.");
        if (this.nodeConfigurations.isEmpty()) this.cloudConsole.send("the master could not locate a registered node!", ICloudConsole.Output.WARN);

        this.validate("module-database");

        if (this.moduleFile.listFiles() != null) {
            for (File file : this.moduleFile.listFiles()) {
                if (file.getName().endsWith(".jar")) {
                    this.instanceLoader.push(file);
                }
            }
        }
        this.instanceLoader.load();
        this.getCloudConsole().send("loaded commands: §a" + this.getCommandManager().getCommands().size() + " §r| loaded modules: §a" + this.getInstanceLoader().modules() + "§r.");

    }

    public void onShutdown() {
        this.getCloudConsole().close();
        Rust.getInstance().getChannelFactory().close();
        Rust.getInstance().getConfigurationHandler().close();
        this.getCloudConsole().send("Successfully shutdown the cloudsystem.");
    }

    public static void boot() {
        try {
            cloud = new RustCloud();
        } catch (MalformedURLException | URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    @SneakyThrows
    public void validate(final String name) {
        final var dest = new File("modules//" + name + ".jar");
        if (!dest.exists())
            this.cloudConsole.send("The §e" + name + " §rmodule could not be found!", ICloudConsole.Output.WARN);
    }

}
