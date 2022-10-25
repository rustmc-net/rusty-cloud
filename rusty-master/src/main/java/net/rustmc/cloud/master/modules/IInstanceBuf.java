package net.rustmc.cloud.master.modules;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 25.10.2022
 */
public interface IInstanceBuf {

    public Class<?> getInstance();

    public InstanceDescriptor getInstanceDescriptor();

    public void terminate();

    public void boot();

    public RustyCloudModule getEmptyInstance();

}
