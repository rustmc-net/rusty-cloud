package net.rustmc.cloud.master.common.modules;

import net.rustmc.cloud.base.util.operations.UnsafeInstanceOperations;
import net.rustmc.cloud.master.exceptions.EmptyInstanceDescriptor;
import net.rustmc.cloud.master.modules.IInstanceBuf;
import net.rustmc.cloud.master.modules.InstanceDescriptor;
import net.rustmc.cloud.master.modules.RustyCloudModule;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 25.10.2022
 */
public class SimpleInstanceBuf implements IInstanceBuf {

    private final Class<?> instance;
    private final InstanceDescriptor descriptor;
    private final RustyCloudModule emptyInstance;

    public SimpleInstanceBuf(Class<?> instance) {
        this.instance = instance;
        if (this.instance.isAnnotationPresent(InstanceDescriptor.class)) {
            this.descriptor = Class.class.getDeclaredAnnotation(InstanceDescriptor.class);
            this.emptyInstance = (RustyCloudModule) UnsafeInstanceOperations.construct(instance);
        } else throw new EmptyInstanceDescriptor(Class.class.getName());
    }

    @Override
    public Class<?> getInstance() {
        return this.instance;
    }

    @Override
    public InstanceDescriptor getInstanceDescriptor() {
        return this.descriptor;
    }

    @Override
    public void terminate() {
        this.emptyInstance.onTerminate();
    }

    @Override
    public void boot() {
        this.emptyInstance.onBoot();
    }

    @Override
    public RustyCloudModule getEmptyInstance() {
        return this.emptyInstance;
    }

}
