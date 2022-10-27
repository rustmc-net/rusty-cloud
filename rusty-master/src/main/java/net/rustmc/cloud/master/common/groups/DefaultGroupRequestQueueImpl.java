package net.rustmc.cloud.master.common.groups;

import net.rustmc.cloud.master.groups.IGroupRequestQueue;
import net.rustmc.cloud.master.groups.IOfflineGroup;
import net.rustmc.cloud.master.groups.IOfflineGroupPool;

import java.util.LinkedList;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 27.10.2022
 */
public class DefaultGroupRequestQueueImpl implements IGroupRequestQueue {

    private final LinkedList<IOfflineGroup> offlineGroups = new LinkedList<>();
    private IOfflineGroup scope;

    @Override
    public void load(final IOfflineGroupPool offlineGroupPool) {
        for (IOfflineGroup offlineGroup : offlineGroupPool)
            offlineGroups.add(offlineGroup);
    }

    @Override
    public int size() {
        return this.offlineGroups.size();
    }

    @Override
    public IOfflineGroup next() {
        final var out = this.offlineGroups.removeFirst();
        this.scope = out;
        return out;
    }

    @Override
    public void flush() {
        this.scope = null;
    }

    @Override
    public void decline() {
        this.offlineGroups.addFirst(scope);
        this.flush();
    }

    @Override
    public boolean hasNext() {
        return this.offlineGroups.size() != 0;
    }
}
