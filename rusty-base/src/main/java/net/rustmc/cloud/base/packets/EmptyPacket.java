package net.rustmc.cloud.base.packets;

import io.netty.buffer.ByteBuf;
import net.rustmc.cloud.base.communicate.CommunicatePacket;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 01.11.2022
 */
public class EmptyPacket extends CommunicatePacket<EmptyPacket> {

    @Override
    public void decode(ByteBuf buf) {
        throw new UnsupportedOperationException("This packet can not be decoded!");
    }

    @Override
    public void encode(ByteBuf buf) {
        throw new UnsupportedOperationException("This packet can not be encoded!");
    }

}
