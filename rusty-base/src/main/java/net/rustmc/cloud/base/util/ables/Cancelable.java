package net.rustmc.cloud.base.util.ables;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 27.10.2022
 */
public interface Cancelable {

    public void cancel();

    public boolean isCanceled();

}
