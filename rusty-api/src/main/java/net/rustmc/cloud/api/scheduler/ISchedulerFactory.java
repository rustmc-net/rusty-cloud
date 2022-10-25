package net.rustmc.cloud.api.scheduler;

import java.util.function.Consumer;

/**
 * this file belongs to the rusty-cloud project.
 *
 * @author Alexander Jilge
 * @since 25.10.2022, Di.
 */
public interface ISchedulerFactory {

    public IScheduler of(final int identifier);

    public IScheduler register();

    public IScheduler register(final int delay, final int period, final Consumer<IScheduler> onTick);

    public IScheduler register(final int delay, final Consumer<IScheduler> onComplete);

}
