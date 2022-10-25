package net.rustmc.cloud.base.packets.input.handshake;

import io.netty.buffer.ByteBuf;
import net.rustmc.cloud.base.communicate.CommunicatePacket;
import net.rustmc.cloud.base.communicate.PacketIdentifier;
import net.rustmc.cloud.base.packets.output.handshake.PacketOutHandshake;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 25.10.2022
 */
@PacketIdentifier(identifier = 'c')
public class PacketInHandshake extends CommunicatePacket<PacketOutHandshake> {

    private String[] groups;

    @Override
    public void decode(ByteBuf buf) {

    }

    @Override
    public void write(ByteBuf buf) {

    }

}
