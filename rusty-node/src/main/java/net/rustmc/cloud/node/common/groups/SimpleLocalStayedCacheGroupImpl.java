package net.rustmc.cloud.node.common.groups;

import net.rustmc.cloud.node.groups.IStatedCacheGroup;

import java.io.File;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 27.10.2022
 */
public class SimpleLocalStayedCacheGroupImpl implements IStatedCacheGroup {

    private final File file;

    public SimpleLocalStayedCacheGroupImpl(File file) {
        this.file = file;
    }

    @Override
    public String getGroupName() {
        return file.getName();
    }

    @Override
    public File getDirectory() {
        return this.file;
    }

}
