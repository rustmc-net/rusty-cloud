package net.rustmc.cloud.api.scheduler;

import java.util.function.Consumer;

/**
 * this file belongs to the rusty-cloud project.
 *
 * @author Alexander Jilge
 * @since 25.10.2022, Di.
 */
public interface ISchedulerHandlerPool {

    public ISchedulerHandlerPool addOnTickHandler(Consumer<IScheduler> handler);

    public ISchedulerHandlerPool addOnCompleteHandler(Consumer<IScheduler> handler);

    public ISchedulerHandlerPool addOnCloseHandler(Consumer<IScheduler> handler);

    public IScheduler getScheduler();

}
