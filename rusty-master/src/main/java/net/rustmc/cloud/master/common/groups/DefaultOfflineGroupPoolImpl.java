package net.rustmc.cloud.master.common.groups;

import net.rustmc.cloud.api.objects.SimpleCloudGroup;
import net.rustmc.cloud.master.RustCloud;
import net.rustmc.cloud.master.groups.IOfflineGroup;
import net.rustmc.cloud.master.groups.IOfflineGroupPool;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 27.10.2022
 */
public class DefaultOfflineGroupPoolImpl implements IOfflineGroupPool {

    private final List<IOfflineGroup> offlineGroups = new ArrayList<>();

    @Override
    public void load() {
        for (SimpleCloudGroup group : RustCloud.getCloud().getGroupsConfiguration().getGroups()) {
            if (!RustCloud.getCloud().getRemoteGroupPool().contains(group.getName())) {
                if (!this.contains(group.getName())) {
                    this.offlineGroups.add(DefaultOfflineGroupImpl.of(group.getName()));
                }
            }
        }
    }

    @Override
    public int size() {
        return this.offlineGroups.size();
    }

    @Override
    public Iterator<IOfflineGroup> iterator() {
        return offlineGroups.iterator();
    }

    protected boolean contains(final String name) {
        for (IOfflineGroup group : this.offlineGroups) {
            if (group.getGroupName().equals(name))
                return true;
        }
        return false;
    }

}
