package net.rustmc.cloud.master.common.groups;

import net.rustmc.cloud.master.groups.IOfflineGroup;
import net.rustmc.cloud.master.groups.IOfflineGroupPool;
import net.rustmc.cloud.master.groups.IRemoteGroup;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 25.10.2022
 */
public class DefaultOfflineGroupImpl implements IOfflineGroup {

    private final String name;

    private DefaultOfflineGroupImpl(String name) {
        this.name = name;
    }

    public static IOfflineGroup of(final String name) {
        return new DefaultOfflineGroupImpl(name);
    }

    @Override
    public String getGroupName() {
        return this.name;
    }

    @Override
    public File getDirectory() {
        return new File("groups//" + name);
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public List<File> getImportantFiles() {
        final var out = new ArrayList<File>();
        final var dir = this.getDirectory();
        if (dir.listFiles() != null) {
            for (File file : dir.listFiles()) {
                if (!file.getName().contains(".jar")) {
                    out.add(file);
                }
            }
        }
        return out;
    }

}
