package net.rustmc.cloud.api.scheduler;

/**
 * this file belongs to the rusty-cloud project.
 *
 * @author Alexander Jilge
 * @since 25.10.2022, Di.
 */
public interface IScheduler {

    public IScheduler period(final int period);

    public IScheduler delay(final int delay);

    public void close();

    public IScheduler run();

    public ISchedulerHandlerPool getHandlerPool();

}
