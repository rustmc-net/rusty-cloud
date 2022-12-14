package net.rustmc.cloud.module.rest;

import net.rustmc.cloud.base.console.ICloudConsole;
import net.rustmc.cloud.master.RustCloud;
import net.rustmc.cloud.master.modules.InstanceDescriptor;
import net.rustmc.cloud.master.modules.RustyCloudModule;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 25.10.2022
 */
@InstanceDescriptor(name = "restdatabase-module", author = "HarmanTBK", version = "alpha-1.0")
public final class DatabasePool extends RustyCloudModule {

    @Override
    public void onBoot() {
    }

    @Override
    public void onTerminate() {
    }

}
