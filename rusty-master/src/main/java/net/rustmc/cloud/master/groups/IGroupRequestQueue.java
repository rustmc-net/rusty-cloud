package net.rustmc.cloud.master.groups;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 27.10.2022
 */
public interface IGroupRequestQueue {

    public void load(final IOfflineGroupPool offlineGroupPool);

    public int size();

    public boolean hasNext();

    public void consume(Predicate<IOfflineGroup> groupConsumer);

}
