package net.rustmc.cloud.master.bootstrap;

import net.rustmc.cloud.master.RustCloud;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
public final class Bootstrap {

    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> RustCloud.getCloud().onShutdown()));

        RustCloud.boot();

    }

}
