package net.rustmc.cloud.base.common.threads;

import net.rustmc.cloud.base.threads.IThread;

import java.util.function.Consumer;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
public class SimpleCloudThread implements IThread {

    private final Consumer<IThread> handler;
    private final Thread core = new Thread() {

        @Override
        public void run() {
            handler.accept(SimpleCloudThread.this);
        }
    };

    public SimpleCloudThread(Consumer<IThread> runnable) {
        this.handler = runnable;
    }

    @Override
    public void close() {
        if (this.core.isAlive())
            this.core.interrupt();
    }

    @Override
    public void run() {
        this.core.start();
    }

    @Override
    public Consumer<IThread> handler() {
        return this.handler;
    }

    @Override
    public boolean isRunning() {
        return this.core.isAlive();
    }

}
