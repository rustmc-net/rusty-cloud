package net.rustmc.cloud.base.threads;

import java.util.function.Consumer;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
public interface IThread {

    public void close();

    public void run();

    public Consumer<IThread> handler();

    public boolean isRunning();

}
