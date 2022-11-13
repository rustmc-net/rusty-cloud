package net.rustmc.cloud.master.common.nodes;

import lombok.Getter;
import net.rustmc.cloud.master.configurations.CloudNodeConfiguration;
import net.rustmc.cloud.master.nodes.IOfflineNode;

import java.io.File;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 13.11.2022
 */
public record SimpleOfflineNode(CloudNodeConfiguration configuration, File file) implements IOfflineNode {
}
