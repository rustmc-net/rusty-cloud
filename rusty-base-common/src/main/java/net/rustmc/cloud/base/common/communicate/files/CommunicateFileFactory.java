package net.rustmc.cloud.base.common.communicate.files;

import net.rustmc.cloud.base.communicate.files.ICommunicateFile;
import net.rustmc.cloud.base.communicate.files.ICommunicateFileFactory;

import java.io.File;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 26.10.2022
 */
public class CommunicateFileFactory implements ICommunicateFileFactory {

    @Override
    public ICommunicateFile of(File file) {
        return new DefaultCommunicateFileImpl(file, null, null);
    }

}
