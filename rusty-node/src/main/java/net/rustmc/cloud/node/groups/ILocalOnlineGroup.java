package net.rustmc.cloud.node.groups;

import java.io.File;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 27.10.2022
 */
public interface ILocalOnlineGroup {

    public String getGroupName();

    public File getDirectory();

}
