package net.rustmc.cloud.base.communicate;

import io.netty.buffer.ByteBuf;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
public abstract class CommunicatePacket<T extends CommunicatePacket<?>> {

    public abstract void decode(final ByteBuf buf);

    public abstract void write(final ByteBuf buf);

}
