package net.rustmc.cloud.master.groups;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 27.10.2022
 */
public interface IOfflineGroupPool extends Iterable<IOfflineGroup> {

    public void load();

    public int size();

}
