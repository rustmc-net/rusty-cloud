package net.rustmc.cloud.node.service;

import java.util.List;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 16.12.2022
 */
public interface INativeCachedConsole {

    public List<String> getLines();

    public void register(String line);

}
