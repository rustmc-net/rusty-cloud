package net.rustmc.cloud.master.nodes;

import net.rustmc.cloud.master.configurations.CloudNodeConfiguration;
import net.rustmc.cloud.master.groups.ICloudGroup;

import java.io.File;
import java.util.List;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 13.11.2022
 */
public interface IOfflineNode {

    public File file();

    public CloudNodeConfiguration configuration();

    public List<ICloudGroup> getAllocatedGroups();

}
