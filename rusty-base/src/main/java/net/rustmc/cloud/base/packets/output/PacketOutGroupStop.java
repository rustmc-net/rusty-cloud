package net.rustmc.cloud.base.packets.output;

import io.netty.buffer.ByteBuf;
import net.rustmc.cloud.base.communicate.CommunicatePacket;
import net.rustmc.cloud.base.objects.PrimitiveGroup;

public class PacketOutGroupStop extends CommunicatePacket<PacketOutGroupStop> {

    private PrimitiveGroup primitiveGroup;

    @Override
    public void decode(ByteBuf buf) {

    }

    @Override
    public void encode(ByteBuf buf) {

    }
}
