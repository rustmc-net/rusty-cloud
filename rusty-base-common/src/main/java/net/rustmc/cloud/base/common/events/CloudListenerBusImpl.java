package net.rustmc.cloud.base.common.events;

import net.rustmc.cloud.base.events.CloudEvent;
import net.rustmc.cloud.base.events.ICloudListenerBus;

import java.util.*;
import java.util.function.Consumer;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
@SuppressWarnings("rawtypes")
public final class CloudListenerBusImpl implements ICloudListenerBus {

    private final Map<Class<? extends CloudEvent>, List<Consumer>> register = new HashMap<>();

    @Override
    public <T extends CloudEvent> int register(Class<? extends T> type, Consumer<T> listener) {
        if (register.containsKey(type)) register.get(type).add(listener);
            else this.register.put(type, new ArrayList<>(Collections.singleton(listener)));
        return register.get(type).size();
    }

    @Override
    public void remove(Class<? extends CloudEvent> type, int uniqueIdentifier) {
        if (this.register.containsKey(type))
            if (this.register.size() > uniqueIdentifier)
                this.register.get(type).remove(uniqueIdentifier-1);
    }

    @Override
    public Collection<Consumer> collect(Class<? extends CloudEvent> type) {
        if (this.register.containsKey(type))
            return this.register.get(type);
        return Collections.emptyList();
    }

}
