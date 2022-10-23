package net.rustmc.cloud.master;

import lombok.Getter;
import net.rustmc.cloud.base.common.Rust;
import net.rustmc.cloud.base.common.dependencies.DependencyHandlerImpl;
import net.rustmc.cloud.base.console.ICloudConsole;
import net.rustmc.cloud.base.dependencies.Dependency;
import net.rustmc.cloud.base.dependencies.IDependencyHandler;

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

    private final ICloudConsole cloudConsole = Rust.getInstance().getConsoleFactory().newConsole();
    private final IDependencyHandler dependencyHandler = new DependencyHandlerImpl();

    public RustCloud() {

        Thread.setDefaultUncaughtExceptionHandler((t, e) -> RustCloud.this
                .getCloudConsole()
                .send(
                        "§7Caught an §cunexpected error §7(§c" + e.getClass().getSimpleName() + "§7) " + "| (§b" + e.getMessage() + "§7)",
                        ICloudConsole.Output.ERROR
                ));

        this.getDependencyHandler()
                .throwIn(Dependency.of("gson", "com.google.code.gson", "gson", "2.9.1"))
                .throwIn(Dependency.of("jline_terminal", "org.jline", "jline-terminal", "3.21.0"))
                .throwIn(Dependency.of("jline", "jline", "jline", "2.14.6"))
                .throwIn(Dependency.of("gson", "org.jline", "jline-reader", "3.21.0"))
                .throwIn(Dependency.of("netty_all", "io.netty", "netty-all", "4.1.84.Final")
                );

    }

    public void onShutdown() {

        this.getCloudConsole().send("Successfully shutdown the cloudsystem.");
        System.exit(0);

    }

    public static void boot() {
        cloud = new RustCloud();
    }

}
