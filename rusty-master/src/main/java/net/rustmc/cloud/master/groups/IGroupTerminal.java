package net.rustmc.cloud.master.groups;

import java.io.File;
import java.util.Collection;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 21.11.2022
 */
public interface IGroupTerminal {

    public void register(File file);

    public Collection<ICloudGroup> getUnallocatedGroups();

    public ICloudGroup getCloudGroupByName(String name);

}
