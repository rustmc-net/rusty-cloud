package net.rustmc.cloud.node.commons.service;

import net.rustmc.cloud.node.service.INativeCachedConsole;

import java.util.LinkedList;
import java.util.List;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 16.12.2022
 */
public class DefaultNativeConsoleImpl implements INativeCachedConsole {

    private final LinkedList<String> lines = new LinkedList<>();

    @Override
    public List<String> getLines() {
        return this.lines;
    }

    @Override
    public void register(String line) {
        this.lines.addLast(line);
    }

}
