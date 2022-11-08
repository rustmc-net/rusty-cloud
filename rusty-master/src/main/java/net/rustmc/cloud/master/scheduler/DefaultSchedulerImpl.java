package net.rustmc.cloud.master.scheduler;

import net.rustmc.cloud.base.scheduler.IScheduler;
import net.rustmc.cloud.base.scheduler.ISchedulerHandlerPool;

/**
 * this file belongs to the rusty-cloud project.
 *
 * @author Alexander Jilge
 * @since 25.10.2022, Di.
 */
@SuppressWarnings("BusyWait")
public final class DefaultSchedulerImpl implements IScheduler {

    private int period = -1, delay = -1;
    private final SimpleSchedulerHandlerPool handlerPool;
    private Thread schedulerThread;

    public DefaultSchedulerImpl(final int uniqueID) {
        this.handlerPool = new SimpleSchedulerHandlerPool(uniqueID);
    }


    @Override
    public IScheduler period(int period) {
        this.period = period;
        return this;
    }

    @Override
    public IScheduler delay(int delay) {
        this.delay = delay;
        return this;
    }

    @Override
    public void close() {
        if (this.schedulerThread != null) {
            this.schedulerThread.interrupt();
        }
    }

    @Override
    public IScheduler run() {
        if (this.period == -1) {
            this.schedulerThread = new Thread(() -> {
                try {
                    Thread.sleep(DefaultSchedulerImpl.this.delay);
                    DefaultSchedulerImpl.this.handlerPool.getOnCompleteHandler().accept(DefaultSchedulerImpl.this);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });
            this.schedulerThread.start();
        } else {
            this.schedulerThread = new Thread(() -> {
                while (!DefaultSchedulerImpl.this.schedulerThread.isInterrupted()) {
                    try {
                        Thread.sleep(DefaultSchedulerImpl.this.delay);
                        DefaultSchedulerImpl.this.handlerPool.getOnTickHandler().accept(DefaultSchedulerImpl.this);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            });
            this.schedulerThread.start();
        }
        return this;
    }

    @Override
    public ISchedulerHandlerPool getHandlerPool() {
        return this.handlerPool;
    }
}
