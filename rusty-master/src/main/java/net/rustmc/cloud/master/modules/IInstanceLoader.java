package net.rustmc.cloud.master.modules;

import java.io.File;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 25.10.2022
 */
public interface IInstanceLoader extends Iterable<IInstanceBuf> {

    public void push(final File file);

    public void load();

    public void terminate();

    public int modules();

}
