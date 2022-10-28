package net.rustmc.cloud.master.groups;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 29.10.2022
 */
public interface IGroupFactory {

    public IOfflineGroup newOfflineGroup(final String name);

}
