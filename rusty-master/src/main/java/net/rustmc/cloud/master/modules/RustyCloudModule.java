package net.rustmc.cloud.master.modules;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 25.10.2022
 */
public abstract class RustyCloudModule {

    /**
     * boot handler of the module
     */
    public abstract void onBoot();

    /**
     * terminate handler of the module
     */
    public abstract void onTerminate();

}
