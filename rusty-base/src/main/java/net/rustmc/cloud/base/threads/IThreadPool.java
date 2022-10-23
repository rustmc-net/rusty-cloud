package net.rustmc.cloud.base.threads;

import java.util.function.Consumer;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
public interface IThreadPool extends Iterable<IThread> {

    public IThread produce(Consumer<IThread> handler);

    public void close();

}
