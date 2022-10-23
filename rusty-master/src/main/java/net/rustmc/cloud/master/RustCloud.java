package net.rustmc.cloud.master;

import lombok.Getter;
import net.rustmc.cloud.base.common.Rust;
import net.rustmc.cloud.base.console.ICloudConsole;

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

    public RustCloud() {

        this.cloudConsole = Rust.getInstance().getConsoleFactory().newConsole();

        Thread.setDefaultUncaughtExceptionHandler((t, e) -> RustCloud.this
                .getCloudConsole()
                .send(
                        "§7Caught an §cunexpected error §7(§c" + e.getClass().getSimpleName() + "§7) " + "| (§b" + e.getMessage() + "§7)",
                        ICloudConsole.Output.ERROR
                ));

        this.cloudConsole.run();

    }

    public void onShutdown() {

        this.getCloudConsole().send("Successfully shutdown the cloudsystem.");
        System.exit(0);

    }

    public static void boot() {
        cloud = new RustCloud();
    }

}
