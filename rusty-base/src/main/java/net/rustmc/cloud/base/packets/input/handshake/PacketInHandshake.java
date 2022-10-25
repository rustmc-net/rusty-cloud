package net.rustmc.cloud.base.packets.input.handshake;

import io.netty.buffer.ByteBuf;
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
@PacketIdentifier(identifier = 'c')
@Getter
public class PacketInHandshake extends CommunicatePacket<PacketOutHandshake> {

    private String[] groups = new String[0];
    private int nodeKey;

    public PacketInHandshake(String[] groups, int nodeKey) {
        this.groups = groups;
        this.nodeKey = nodeKey;
    }

    public PacketInHandshake(int nodeKey) {
        this.nodeKey = nodeKey;
    }

    @Override
    public void decode(ByteBuf buf) {
        this.nodeKey = buf.readInt();
        this.groups = ByteBufHelper.readDynamicArray(buf);
    }

    @Override
    public void encode(ByteBuf buf) {
        buf.writeInt(nodeKey);
        ByteBufHelper.writeDynamicArray(this.groups, buf);
    }

}
