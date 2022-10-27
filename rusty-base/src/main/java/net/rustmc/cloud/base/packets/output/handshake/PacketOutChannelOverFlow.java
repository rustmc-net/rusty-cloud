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
@PacketIdentifier(identifier = 'e')
public final class PacketOutChannelOverFlow extends CommunicatePacket<PacketOutChannelOverFlow> {

    public PacketOutChannelOverFlow() {
    }

    @Override
    public void decode(ByteBuf buf) {
    }

    @Override
    public void encode(ByteBuf buf) {
    }

}
