package net.rustmc.cloud.master.groups;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 27.10.2022
 */
public interface IGroupRequestQueue {

    public void load(final IOfflineGroupPool offlineGroupPool);

    public int size();

    public IOfflineGroup next();

    public void flush();

    public void decline();

    public boolean hasNext();

}
