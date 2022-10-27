package net.rustmc.cloud.api.events.node;

import net.rustmc.cloud.api.objects.SimpleCloudNode;
import net.rustmc.cloud.base.events.CloudEvent;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 27.10.2022
 */
public class CloudNodeConnectCompleteEvent extends CloudNodeEvent {

    public CloudNodeConnectCompleteEvent(SimpleCloudNode node) {
        super(node);
    }

}
