package net.rustmc.cloud.master.nodes;

import net.rustmc.cloud.master.configurations.CloudNodeConfiguration;

import java.io.File;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 13.11.2022
 */
public interface IOfflineNode {

    public File file();

    public CloudNodeConfiguration configuration();

}
