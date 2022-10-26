package net.rustmc.cloud.node.storage;

import java.util.Collection;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 26.10.2022
 */
public interface IStorageFactory {

    public Collection<IOfflineStorage> getOfflineStorages();

    public String[] getOfflineStoragesAsArray();

}
