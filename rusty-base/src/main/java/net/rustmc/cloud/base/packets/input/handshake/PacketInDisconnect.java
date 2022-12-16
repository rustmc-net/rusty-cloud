package net.rustmc.cloud.base.packets.input.handshake;

import io.netty.buffer.ByteBuf;
import net.rustmc.cloud.base.communicate.CommunicatePacket;
import net.rustmc.cloud.base.communicate.PacketIdentifier;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 21.11.2022
 */
@PacketIdentifier(identifier = 'a')
public class PacketInDisconnect extends CommunicatePacket<PacketInDisconnect> {

    @Override
    public void decode(ByteBuf buf) {
    }

    @Override
    public void encode(ByteBuf buf) {
    }

}
