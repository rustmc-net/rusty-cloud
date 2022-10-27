package net.rustmc.cloud.node.common.groups;

import net.rustmc.cloud.node.RustCloud;
import net.rustmc.cloud.node.groups.IGroupFactory;
import net.rustmc.cloud.node.groups.ILocalOnlineGroup;
import net.rustmc.cloud.node.groups.IStatedCacheGroup;

import java.io.File;
import java.util.Collection;
import java.util.LinkedList;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 27.10.2022
 */
public class DefaultGroupFactoryImpl implements IGroupFactory {

    private final LinkedList<IStatedCacheGroup> statedCacheGroups = new LinkedList<>();
    private final LinkedList<ILocalOnlineGroup> localOnlineGroups = new LinkedList<>();

    @Override
    public Collection<IStatedCacheGroup> getStayedCacheGroups() {
        return this.statedCacheGroups;
    }

    @Override
    public Collection<ILocalOnlineGroup> getLocalOnlineGroups() {
        return this.localOnlineGroups;
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public void loadStayedCacheGroups() {
        if (RustCloud.getCloud().getStorageFile().listFiles() != null) {
            for (File file : RustCloud.getCloud().getStorageFile().listFiles()) {
                if (file.isDirectory()) {
                    this.statedCacheGroups.addLast(new SimpleLocalStayedCacheGroupImpl(file));
                }
            }
        }
    }

    @Override
    public void logIn(ILocalOnlineGroup localOnlineGroup) {
        this.localOnlineGroups.addLast(localOnlineGroup);
    }
}
