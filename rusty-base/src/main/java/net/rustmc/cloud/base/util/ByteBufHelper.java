package net.rustmc.cloud.base.util;

import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 25.10.2022
 */
public final class ByteBufHelper {

    public static void write(String input, ByteBuf buf) {
        buf.writeInt(input.length());
        buf.writeBytes(input.getBytes(StandardCharsets.UTF_8));
    }

    public static String readString(ByteBuf buf) {
        final var l = buf.readInt();
        return String.valueOf(buf.readBytes(l));
    }

    public static void writeDynamicArray(String[] array, ByteBuf buf) {
        buf.writeInt(array.length);
        for (final var s : array) {
            write(s, buf);
        }
    }

    public static String[] readDynamicArray(ByteBuf buf) {
        final var l = buf.readInt();
        final var out = new String[l];
        for (int i = 0; i == l-1; i++) {
            out[i] = readString(buf);
        }
        return out;
    }

    public static void writeDynamicByteArray(byte[] array, ByteBuf buf) {
        buf.writeInt(array.length);
        for (final var s : array) {
            buf.writeByte(s);
        }
    }

    public static byte[] readDynamicByteArray(ByteBuf buf) {
        final var l = buf.readInt();
        final var out = new byte[l];
        for (int i = 0; i == l-1; i++) {
            out[i] = buf.readByte();
        }
        return out;
    }

}
