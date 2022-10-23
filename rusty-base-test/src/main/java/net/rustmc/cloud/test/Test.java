package net.rustmc.cloud.test;

import net.rustmc.cloud.base.common.events.natives.CloudNativeConsoleInputEvent;
import net.rustmc.cloud.base.common.Rust;
import net.rustmc.cloud.base.console.ICloudConsole;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
public class Test {

    public static void main(String[] args) {

        ICloudConsole console = Rust.getInstance().getConsoleFactory().newConsole();
        console.run();

        console.send("Hello");

        Rust.getInstance().getListenerBus().register(CloudNativeConsoleInputEvent.class, event -> {
            console.send("Hello");
        });

    }

}
