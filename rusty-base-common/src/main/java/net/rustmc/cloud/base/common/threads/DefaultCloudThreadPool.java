package net.rustmc.cloud.base.common.threads;

import lombok.val;
import net.rustmc.cloud.base.threads.IThread;
import net.rustmc.cloud.base.threads.IThreadPool;

import java.util.Iterator;
import java.util.function.Consumer;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
public class DefaultCloudThreadPool implements IThreadPool {

    private IThread[] pool = new IThread[0];

    @Override
    public IThread produce(final Consumer<IThread> handler) {
        val temp = new IThread[this.pool.length+1];
        val thread = new SimpleCloudThread(handler);
        temp[this.pool.length] = thread;
        this.pool = temp;
        return thread;
    }

    @Override
    public void close() {
        for (val thread : this.pool)
            thread.close();
        this.pool = new IThread[0];
    }

    @Override
    public Iterator<IThread> iterator() {
        return new Iterator<>() {

            int r = 0;

            @Override
            public boolean hasNext() {
                return pool.length > r;
            }

            @Override
            public IThread next() {
                val out = pool[this.r];
                r++;
                return out;
            }
        };
    }
}
