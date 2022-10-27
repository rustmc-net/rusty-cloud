package net.rustmc.cloud.master.common.groups;

import net.rustmc.cloud.master.groups.IRemoteGroupPool;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 25.10.2022
 */
public class DefaultRemoteGroupPoolImpl implements IRemoteGroupPool {

    @Override
    public boolean contains(String name) {
        return false;
    }

}
