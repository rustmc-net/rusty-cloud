package net.rustmc.cloud.node.groups;

import java.util.Collection;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 21.11.2022
 */
public interface IOfflineGroupTerminal {

    public Collection<IOfflineGroup> collect();

    public Collection<String> collectNames();

    public boolean contains(String name);

}
