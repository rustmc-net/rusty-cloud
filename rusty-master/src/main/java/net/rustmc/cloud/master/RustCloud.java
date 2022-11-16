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
import net.rustmc.cloud.base.communicate.ICommunicateChannel;
import net.rustmc.cloud.base.console.ICloudConsole;
import net.rustmc.cloud.base.util.FileHelper;
import net.rustmc.cloud.master.commands.CloseCommand;
import net.rustmc.cloud.master.commands.ProduceCommand;
import net.rustmc.cloud.master.common.modules.DefaultInstanceLoaderImpl;
import net.rustmc.cloud.master.common.nodes.CloudOfflineNodeTerminalImpl;
import net.rustmc.cloud.master.configurations.CloudBaseConfiguration;
import net.rustmc.cloud.master.configurations.CloudGroupsConfiguration;
import net.rustmc.cloud.master.modules.IInstanceLoader;
import net.rustmc.cloud.master.nodes.IOfflineNodeTerminal;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
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
    private final CommandManager commandManager = new CommandManager();
    private final File nodeFile = new File("nodes");
    private final File moduleFile = new File("modules");
    private final File groupFile = new File("groups");
    private final File tempFile = new File("temp");
    private final IInstanceLoader instanceLoader = new DefaultInstanceLoaderImpl();
    private final ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
    private final CloudBaseConfiguration baseCloudConfiguration = Rust.getInstance()
            .getConfigurationHandler()
            .open(new File("base.json").toURI(), CloudBaseConfiguration.class);
    private final CloudGroupsConfiguration groupsConfiguration = Rust.getInstance()
            .getConfigurationHandler()
            .open(new File("groups.json").toURI(), CloudGroupsConfiguration.class);
    private ICommunicateBaseChannel communicateChannel;
    private final IOfflineNodeTerminal offlineNodeTerminal = new CloudOfflineNodeTerminalImpl();

    @SuppressWarnings("DataFlowIssue")
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

        if (this.nodeFile.listFiles() != null) {
            for (File file : this.nodeFile.listFiles()) {
                this.offlineNodeTerminal.registerCloudNodeConfiguration(file);
            }
        }

    }

    @SuppressWarnings("ConstantConditions")
    public void onBoot() {

        new ConstantPacketRegistryCluster();

        this.validate("module-database");
        this.validate("module-rest");

        if (this.moduleFile.listFiles() != null) {
            for (File file : this.moduleFile.listFiles()) {
                if (file.getName().endsWith(".jar")) {
                    this.instanceLoader.push(file);
                }
            }
        }
        this.instanceLoader.load();
        this.getCloudConsole().send("loaded commands: §a" + this.getCommandManager().getCommands().size() + " §r| loaded modules: §a" + this.getInstanceLoader().modules() + "§r.");

        try {

            this.communicateChannel = Rust.getInstance()
                    .getChannelFactory()
                    .bootstrap()
                    .port(this.baseCloudConfiguration.getPort())
                    .open();

            this.getCloudConsole().send("cloud channel succesfully opened on port §a" + this.baseCloudConfiguration.getPort() + "§r.");

        } catch (ConnectFailException e) {
            throw new RuntimeException(e);
        }

        this.cloudConsole.send("waits for §e" + this.offlineNodeTerminal.getOfflineNodes().size() + " §rregistered nodes in the cache.");

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
