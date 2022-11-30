package net.rustmc.cloud.base.util;

import java.io.*;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 24.10.2022
 */
public final class FileHelper {

    public static void download(final String destination, final URL url) throws IOException {
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(destination);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
        fos.close();
        rbc.close();
    }

    public static void copyFile(String sourceFile, String destination)
            throws IOException {
        try (InputStream in = new FileInputStream(sourceFile);
             OutputStream out = new FileOutputStream(destination)) {
            byte[] buf = new byte[1024];
            int length;
            while ((length = in.read(buf)) > 0) {
                out.write(buf, 0, length);
            }
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static File create(final File file) {
        if (!file.exists()) {
            if (!file.getName().contains(".")) file.mkdirs();
                else {
                try {
                    final var parent = file.getParentFile();
                    if (parent.isDirectory())
                        if (!parent.exists())
                            parent.mkdirs();
                    file.createNewFile();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return file;
    }

    public static void write(File file, String input) {
        try {
            final var outStream = new FileOutputStream(file);
            outStream.write(input.getBytes(StandardCharsets.UTF_8));
            outStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
