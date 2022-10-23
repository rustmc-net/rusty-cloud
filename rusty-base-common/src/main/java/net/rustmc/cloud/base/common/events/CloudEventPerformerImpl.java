package net.rustmc.cloud.base.common.events;

import net.rustmc.cloud.base.common.Rust;
import net.rustmc.cloud.base.events.CloudEvent;
import net.rustmc.cloud.base.events.CloudEventDescriptor;
import net.rustmc.cloud.base.events.ICloudEventPerformer;

import java.util.function.Consumer;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
public final class CloudEventPerformerImpl implements ICloudEventPerformer {

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public <T extends CloudEvent> void perform(T event) {
        boolean sync = true;
        if (event.getClass().isAnnotationPresent(CloudEventDescriptor.class)) {
            final CloudEventDescriptor eventDescriptor = event.getClass().getAnnotation(CloudEventDescriptor.class);
            if (eventDescriptor.async()) sync = false;
        }
        if (sync) {
            for (Consumer handler : Rust.getInstance().getListenerBus().collect(event.getClass())) {
                handler.accept(event);
            }
        } else {
            Rust.getInstance().getAsynchronousExecutor().submit(() -> {
                for (Consumer handler : Rust.getInstance().getListenerBus().collect(event.getClass())) {
                    handler.accept(event);
                }
            });
        }
    }

}
