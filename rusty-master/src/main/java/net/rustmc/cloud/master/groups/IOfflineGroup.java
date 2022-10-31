package net.rustmc.cloud.master.groups;

import net.rustmc.cloud.api.objects.SimpleCloudGroup;

import java.io.File;
import java.util.List;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 25.10.2022
 */
public interface IOfflineGroup {

    String getGroupName();

    File getDirectory();

    List<File> getImportantFiles();

    public SimpleCloudGroup getObject();

}
