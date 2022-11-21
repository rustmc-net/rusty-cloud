package net.rustmc.cloud.master.common.groups;

import net.rustmc.cloud.base.common.Rust;
import net.rustmc.cloud.base.objects.SimpleCloudGroup;
import net.rustmc.cloud.master.RustCloud;
import net.rustmc.cloud.master.configurations.CloudGroupConfiguration;
import net.rustmc.cloud.master.groups.ICloudGroup;
import net.rustmc.cloud.master.groups.IGroupTerminal;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 21.11.2022
 */
public class GroupTerminalImpl implements IGroupTerminal {

    private final List<ICloudGroup> groups = new ArrayList<>();

    @Override
    public void register(File file) {
        final var object = Rust.getInstance()
                .getConfigurationHandler()
                .open(file.getName().replace("json", ""), file.toURI(), CloudGroupConfiguration.class);
        this.groups.add(new SimpleCloudGroupImpl(object.cloudGroup()));
    }

    @Override
    public Collection<ICloudGroup> getUnallocatedGroups() {
        return groups.stream().filter(iCloudGroup -> iCloudGroup.getObject().getAllocatedNode().equals("null")).toList();
    }

    @Override
    public ICloudGroup getCloudGroupByName(String name) {
        for (ICloudGroup group : this.groups) {
            if (group.getObject().getName().equals(name)) return group;
        }
        return null;
    }

}
