package net.rustmc.cloud.master.scheduler;

import lombok.Getter;
import net.rustmc.cloud.base.scheduler.IScheduler;
import net.rustmc.cloud.base.scheduler.ISchedulerHandlerPool;

import java.util.function.Consumer;

/**
 * this file belongs to the rusty-cloud project.
 *
 * @author Alexander Jilge
 * @since 25.10.2022, Di.
 */
@Getter
public final class SimpleSchedulerHandlerPool implements ISchedulerHandlerPool {

    final int uniqueID;
    private Consumer<IScheduler> onTickHandler;
    private Consumer<IScheduler> onCompleteHandler;
    private Consumer<IScheduler> onCloseHandler;

    public SimpleSchedulerHandlerPool(int uniqueID) {
        this.uniqueID = uniqueID;
    }

    @Override
    public ISchedulerHandlerPool addOnTickHandler(Consumer<IScheduler> handler) {
        this.onTickHandler = handler;
        return this;
    }

    @Override
    public ISchedulerHandlerPool addOnCompleteHandler(Consumer<IScheduler> handler) {
        this.onCompleteHandler = handler;
        return this;
    }

    @Override
    public ISchedulerHandlerPool addOnCloseHandler(Consumer<IScheduler> handler) {
        this.onCloseHandler = handler;
        return this;
    }

    @Override
    public IScheduler getScheduler() {
        return SchedulerFactoryImpl.getDefaultFactory().of(this.uniqueID);
    }
}
