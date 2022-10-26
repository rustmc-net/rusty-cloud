package net.rustmc.cloud.base.communicate.files;

import java.io.File;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 26.10.2022
 */
public interface ICommunicateFileFactory {

    public ICommunicateFile of(File file);

}
