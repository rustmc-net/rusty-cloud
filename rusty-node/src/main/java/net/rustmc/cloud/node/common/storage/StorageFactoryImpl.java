package net.rustmc.cloud.node.common.storage;

import net.rustmc.cloud.node.RustCloud;
import net.rustmc.cloud.node.storage.IOfflineStorage;
import net.rustmc.cloud.node.storage.IStorageFactory;

import java.util.Collection;
import java.util.LinkedList;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 26.10.2022
 */
public class StorageFactoryImpl implements IStorageFactory {

    private final LinkedList<IOfflineStorage> storages = new LinkedList<>();

    @SuppressWarnings("ConstantConditions")
    public StorageFactoryImpl() {
        if (RustCloud.getCloud().getStorageFile().listFiles() != null) {
            for (final var directory : RustCloud.getCloud().getStorageFile().listFiles()) {
                if (SimpleOfflineStorage.validate(directory)) {
                    storages.addLast(SimpleOfflineStorage.of(directory));
                }
            }
        }
    }

    @Override
    public Collection<IOfflineStorage> getOfflineStorages() {
        return this.storages;
    }

}
