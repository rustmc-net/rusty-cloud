package net.rustmc.cloud.base.service;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 16.12.2022
 */
public interface IOnlineService {

    public void shutdown();

    public String getName();

    public void command(String command);

}
