package net.rustmc.cloud.api.events.node;

import lombok.Getter;
import net.rustmc.cloud.base.objects.SimpleCloudNode;
import net.rustmc.cloud.base.events.CloudEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 27.10.2022
 */
@Getter
public abstract class CloudNodeEvent extends CloudEvent {

    @Getter
    protected static final Map<Class<? extends CloudEvent>, Boolean> cancelMap = new HashMap<>();

    protected final SimpleCloudNode node;

    public CloudNodeEvent(SimpleCloudNode node) {
        this.node = node;
    }

}
