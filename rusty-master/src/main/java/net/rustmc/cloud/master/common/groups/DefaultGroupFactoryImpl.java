package net.rustmc.cloud.master.common.groups;

import net.rustmc.cloud.master.groups.IGroupFactory;
import net.rustmc.cloud.master.groups.IOfflineGroup;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 29.10.2022
 */
public class DefaultGroupFactoryImpl implements IGroupFactory {

    @Override
    public IOfflineGroup newOfflineGroup(String name) {
        return DefaultOfflineGroupImpl.of(name);
    }

}
