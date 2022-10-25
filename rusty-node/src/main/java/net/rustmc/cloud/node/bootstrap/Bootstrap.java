package net.rustmc.cloud.node.bootstrap;

import net.rustmc.cloud.node.RustCloud;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 26.10.2022
 */
public final class Bootstrap {

    public static void main(String[] args) {

        Runtime.getRuntime().addShutdownHook(new Thread(() -> RustCloud.getCloud().onShutdown()));

        RustCloud.boot();

        RustCloud.getCloud().onBoot();

    }

}
