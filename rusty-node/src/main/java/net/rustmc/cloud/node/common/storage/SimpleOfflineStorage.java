package net.rustmc.cloud.node.common.storage;

import net.rustmc.cloud.node.storage.IOfflineStorage;

import java.io.File;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 26.10.2022
 */
public abstract class SimpleOfflineStorage implements IOfflineStorage {

    private final String path;
    private final File storageDirectory;

    private SimpleOfflineStorage(String path) {
        this.path = path;
        this.storageDirectory = new File(path);
    }

    private SimpleOfflineStorage(File storageDirectory) {
        this.storageDirectory = storageDirectory;
        this.path = storageDirectory.getPath();
    }

    public static IOfflineStorage of(File file) {
        return new SimpleOfflineStorage(file) {
        };
    }

    @SuppressWarnings("ConstantConditions")
    public static boolean validate(File file) {
        if (file.isDirectory()) {
            if (file.listFiles() != null) {
                for (File child : file.listFiles()) {
                    if (child.getName().contains("rusty.json")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public File getStorageDirectory() {
        return this.storageDirectory;
    }

    @Override
    public String getName() {
        return this.storageDirectory.getName();
    }

    @Override
    public String getPath() {
        return path;
    }

}
