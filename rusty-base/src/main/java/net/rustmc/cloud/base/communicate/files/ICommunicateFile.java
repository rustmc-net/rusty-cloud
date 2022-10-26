package net.rustmc.cloud.base.communicate.files;

import io.netty.buffer.ByteBuf;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 26.10.2022
 */
public interface ICommunicateFile {

    public void decode(final ByteBuf buf);

    public void encode(final ByteBuf buf);

}
