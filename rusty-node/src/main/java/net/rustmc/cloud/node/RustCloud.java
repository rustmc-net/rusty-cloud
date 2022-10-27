package net.rustmc.cloud.node;

import lombok.Getter;
import net.rustmc.cloud.api.commands.CommandManager;
import net.rustmc.cloud.api.commands.listeners.ConsoleInputListener;
import net.rustmc.cloud.api.commands.listeners.ConsoleTabListener;
import net.rustmc.cloud.base.common.Rust;
import net.rustmc.cloud.base.common.packets.ConstantPacketRegistryCluster;
import net.rustmc.cloud.base.communicate.IChannelBootstrap;
import net.rustmc.cloud.base.communicate.ICommunicateBaseChannel;
import net.rustmc.cloud.base.console.ICloudConsole;
import net.rustmc.cloud.base.packets.input.handshake.PacketInHandshake;
import net.rustmc.cloud.base.util.FileHelper;
import net.rustmc.cloud.node.commands.CloseCommand;
import net.rustmc.cloud.node.common.DefaultMemoryImpl;
import net.rustmc.cloud.node.common.groups.DefaultGroupFactoryImpl;
import net.rustmc.cloud.node.common.storage.StorageFactoryImpl;
import net.rustmc.cloud.node.configurations.RustyNodeConfiguration;
import net.rustmc.cloud.node.groups.IGroupFactory;
import net.rustmc.cloud.node.handlers.PacketOutHandshakeHandler;
import net.rustmc.cloud.node.handlers.PacketOutNodeMemoryHandler;
import net.rustmc.cloud.node.memory.IMemoryMonitor;
import net.rustmc.cloud.node.storage.IStorageFactory;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 26.10.2022
 */
@Getter
public final class RustCloud {

    @Getter
    private static RustCloud cloud;

    private final ICloudConsole cloudConsole;
    private final IChannelBootstrap bootstrap = Rust.getInstance().getChannelFactory().bootstrap();
    private final CommandManager commandManager = new CommandManager();
    private final RustyNodeConfiguration configuration = Rust.getInstance().getConfigurationHandler().open("node", new File("node.json").toURI(), RustyNodeConfiguration.class);
    private final File storageFile = new File("storages");
    private final IStorageFactory storageFactory = new StorageFactoryImpl(this);
    private ICommunicateBaseChannel communicateBaseChannel;
    private final IGroupFactory groupFactory = new DefaultGroupFactoryImpl();
    private final IMemoryMonitor memoryMonitor = new DefaultMemoryImpl();

    public RustCloud() {

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

            Rust.getInstance().getConfigurationHandler().update();

        });

        this.cloudConsole.run();

        new ConsoleInputListener(this.getCommandManager());
        new ConsoleTabListener(this.getCommandManager());

        this.getCommandManager().register(new CloseCommand());

        FileHelper.create(this.storageFile);

    }

    public void onBoot() {

        this.groupFactory.loadStayedCacheGroups();

        this.cloudConsole.send("Connecting to §e" + this.configuration.getHost() + " §rat port §e" + this.configuration.getPort() + "§r.");

        new ConstantPacketRegistryCluster();

        try {
            this.communicateBaseChannel = this.bootstrap
                    .host(this.configuration.getHost())
                    .port(this.configuration.getPort())
                    .open();

            new PacketOutHandshakeHandler();
            new PacketOutNodeMemoryHandler();

            this.communicateBaseChannel.dispatch(
                    new PacketInHandshake(
                            this.storageFactory.getOfflineStoragesAsArray(),
                            this.configuration.getNodeKey()
                    )
            );

            this.getGroupFactory().requestAsynchronousForStayed();
            Thread.currentThread().wait(300);
            this.getMemoryMonitor().update();

            this.cloudConsole.send("§a" + this.getMemoryMonitor().getFreeMemoryFromRemote() + " §rmb ram can still be allocated§r.");

        } catch (Exception ignored) {
        }

    }

    public void onShutdown() {
        this.getCloudConsole().close();
        Rust.getInstance().getChannelFactory().close();
        Rust.getInstance().getConfigurationHandler().close();
        this.getCloudConsole().send("Successfully shutdown the node.");
    }

    public static void boot() {
        cloud = new RustCloud();
    }

}
