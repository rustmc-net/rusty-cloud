package net.rustmc.cloud.base.console;

import org.jline.reader.LineReader;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
public interface ICloudConsoleFactory {

    public LineReader getCloudLineReader();

    public ICloudConsole newConsole();

    public LineReader newLineReader();

}
