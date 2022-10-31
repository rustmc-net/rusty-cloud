package net.rustmc.cloud.master.common.groups;

import net.rustmc.cloud.master.groups.IGroupRequestQueue;
import net.rustmc.cloud.master.groups.IOfflineGroup;
import net.rustmc.cloud.master.groups.IOfflineGroupPool;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 27.10.2022
 */
public class DefaultGroupRequestQueueImpl implements IGroupRequestQueue {

    private final LinkedList<IOfflineGroup> offlineGroups = new LinkedList<>();

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
    public boolean hasNext() {
        return this.offlineGroups.size() != 0;
    }

    @Override
    public void consume(Predicate<IOfflineGroup> groupConsumer) {
        this.offlineGroups.sort(new Comparator<IOfflineGroup>() {
            @Override
            public int compare(IOfflineGroup o1, IOfflineGroup o2) {
                return Math.min(o1.getObject().getMemory(), o2.getObject().getMemory());
            }
        });
        int i = 0;
        for (IOfflineGroup group : offlineGroups) {
            boolean b = groupConsumer.test(group);
            if (b) {
                offlineGroups.remove(i);
            }
            i++;
        }
    }


}
