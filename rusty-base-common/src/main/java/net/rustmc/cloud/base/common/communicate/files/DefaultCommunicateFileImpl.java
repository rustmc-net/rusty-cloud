package net.rustmc.cloud.base.common.communicate.files;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import lombok.SneakyThrows;
import net.rustmc.cloud.base.communicate.files.ICommunicateFile;
import net.rustmc.cloud.base.util.ByteBufHelper;
import net.rustmc.cloud.base.util.FileHelper;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 26.10.2022
 */
@Getter
public class DefaultCommunicateFileImpl implements ICommunicateFile {

    private final File file;
    private String name;
    private byte[] content;

    public DefaultCommunicateFileImpl(File file, String name, byte[] content) {
        this.file = file;
        this.name = name;
        this.content = content;
    }

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
        this.name = ByteBufHelper.readString(buf);
        final var length = buf.readInt();
        this.content = buf.readBytes(length).array();
    }

    @Override
    public void create(String path) {
        if (this.content != null)
            if (this.name != null) {
                final var file = FileHelper.create(new File(path + this.name));
                try {
                    final var os = new FileOutputStream(file.getPath());
                    os.write(this.content);
                    os.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
    }

}
