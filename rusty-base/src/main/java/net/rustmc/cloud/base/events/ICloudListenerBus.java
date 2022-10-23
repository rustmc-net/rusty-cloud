package net.rustmc.cloud.base.events;

import java.util.Collection;
import java.util.function.Consumer;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
@SuppressWarnings("rawtypes")
public interface ICloudListenerBus {

    public <T extends CloudEvent> int register(Class<? extends T> type, Consumer<T> listener);

    public void remove(Class<? extends CloudEvent> type,int uniqueIdentifier);

    public Collection<Consumer> collect(Class<? extends CloudEvent> type);

}
