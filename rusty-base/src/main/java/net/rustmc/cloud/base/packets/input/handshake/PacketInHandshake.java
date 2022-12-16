package net.rustmc.cloud.base.packets.input.handshake;

import io.netty.buffer.ByteBuf;
import io.netty.util.ByteProcessor;
import lombok.Getter;
import net.rustmc.cloud.base.communicate.CommunicatePacket;
import net.rustmc.cloud.base.communicate.PacketIdentifier;
import net.rustmc.cloud.base.packets.output.handshake.PacketOutHandshake;
import net.rustmc.cloud.base.util.ByteBufHelper;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 25.10.2022
 */
@PacketIdentifier(identifier = 'b')
@Getter
public class PacketInHandshake extends CommunicatePacket<PacketOutHandshake> {

    private int nodeKey;

    public PacketInHandshake() {
    }

    public PacketInHandshake(int nodeKey) {
        this.nodeKey = nodeKey;
    }

    @Override
    public void decode(ByteBuf buf) {
        this.nodeKey = buf.readInt();
    }

    @Override
    public void encode(ByteBuf buf) {
        buf.writeInt(nodeKey);
    }

}
