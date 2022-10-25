package net.rustmc.cloud.base.packets.output.handshake;

import io.netty.buffer.ByteBuf;
import net.rustmc.cloud.base.communicate.CommunicatePacket;
import net.rustmc.cloud.base.communicate.PacketIdentifier;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 25.10.2022
 */
@PacketIdentifier(identifier = 'o')
public class PacketOutHandshake extends CommunicatePacket<PacketOutHandshake> {

    @Override
    public void decode(ByteBuf buf) {

    }

    @Override
    public void write(ByteBuf buf) {

    }

}
