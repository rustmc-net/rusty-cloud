package net.rustmc.cloud.master.common.nodes;

import lombok.Getter;
import net.rustmc.cloud.master.RustCloud;
import net.rustmc.cloud.master.configurations.CloudNodeConfiguration;
import net.rustmc.cloud.master.groups.ICloudGroup;
import net.rustmc.cloud.master.nodes.IOfflineNode;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 13.11.2022
 */
public record SimpleOfflineNode(CloudNodeConfiguration configuration, File file) implements IOfflineNode {
    @Override
    public List<ICloudGroup> getAllocatedGroups() {
        return new ArrayList<>(
                RustCloud.getCloud()
                        .getGroupTerminal()
                        .getCloudGroups().stream().filter(group -> group.getObject()
                                .getAllocatedNode()
                                .equals(
                                        SimpleOfflineNode.this.configuration.getName()
                                )).toList()
        );
    }
}
