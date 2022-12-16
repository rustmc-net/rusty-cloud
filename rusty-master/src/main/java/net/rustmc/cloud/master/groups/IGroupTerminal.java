package net.rustmc.cloud.master.groups;

import java.io.File;
import java.util.Collection;
import java.util.List;

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

    public ICloudGroup produce(String name, boolean proxy, int maxPlayersPer, int percent, int maxServers, int memory, String allocatedNode);

    public void requestTransfer(ICloudGroup group);

    public List<ICloudGroup> getCloudGroups();

}
