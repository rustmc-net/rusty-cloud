package net.rustmc.cloud.api.events.node;

import net.rustmc.cloud.api.objects.SimpleCloudNode;
import net.rustmc.cloud.base.util.ables.Cancelable;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 27.10.2022
 */
public class CloudNodeConnectEvent extends CloudNodeEvent implements Cancelable {

    public CloudNodeConnectEvent(SimpleCloudNode node) {
        super(node);
    }

    @Override
    public void cancel() {
        cancelMap.put(this.getClass(), true);
    }

    @Override
    public boolean isCanceled() {
        if (!cancelMap.containsKey(this.getClass())) return false;
            else return cancelMap.containsKey(this.getClass());
    }

    public static boolean flush() {
        if (!cancelMap.containsKey(CloudNodeConnectEvent.class)) return false;
        final var out = cancelMap.get(CloudNodeConnectEvent.class);
        cancelMap.remove(CloudNodeConnectEvent.class);
        return out;
    }

}
