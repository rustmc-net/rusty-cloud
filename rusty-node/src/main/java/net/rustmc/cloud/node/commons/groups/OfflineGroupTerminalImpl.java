package net.rustmc.cloud.node.commons.groups;

import net.rustmc.cloud.node.RustCloud;
import net.rustmc.cloud.node.groups.IOfflineGroup;
import net.rustmc.cloud.node.groups.IOfflineGroupTerminal;

import java.util.*;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 21.11.2022
 */
public class OfflineGroupTerminalImpl implements IOfflineGroupTerminal {

    @Override
    public Collection<IOfflineGroup> collect() {
        return null;
    }

    @SuppressWarnings("DataFlowIssue")
    @Override
    public Collection<String> collectNames() {
        if (RustCloud.getCloud().getGroupsFile().listFiles() != null) {
            final List<String> storage = new ArrayList<>();
            for (final var file : RustCloud.getCloud().getGroupsFile().listFiles()) {
                if (file.isDirectory()) storage.add(file.getName());
            }
            return storage;
        } else return Collections.emptyList();
    }

    @Override
    public boolean contains(String name) {
        return collectNames().contains(name);
    }
}
