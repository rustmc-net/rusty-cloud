package net.rustmc.cloud.node.storage;

import java.io.File;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 26.10.2022
 */
public interface IOfflineStorage {

    public String getName();

    public String getPath();

    public File getStorageDirectory();

}
