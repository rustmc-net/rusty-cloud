package net.rustmc.cloud.base.common.communicate.files;

import io.netty.buffer.ByteBuf;
import lombok.SneakyThrows;
import net.rustmc.cloud.base.communicate.files.ICommunicateFile;
import net.rustmc.cloud.base.util.ByteBufHelper;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 26.10.2022
 */
public class DefaultCommunicateFileImpl implements ICommunicateFile {

    private File file;

    @SneakyThrows
    @Override
    public void decode(ByteBuf buf) {
        if (!file.isDirectory()) {
            ByteBufHelper.write(file.getName(), buf);
            byte[] buffer = new byte[4096];
            final var ous = new ByteArrayOutputStream();
            InputStream ios = new FileInputStream(this.file);
            int read = 0;
            while ((read = ios.read(buffer)) != -1) {
                ous.write(buffer, 0, read);
            }
            ous.close();
            ios.close();
            final var out = ous.toByteArray();
            buf.writeInt(out.length);
            buf.writeBytes(out);
        }
    }

    @Override
    public void encode(ByteBuf buf) {
    }

}
