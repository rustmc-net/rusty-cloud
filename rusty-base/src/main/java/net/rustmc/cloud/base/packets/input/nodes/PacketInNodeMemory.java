package net.rustmc.cloud.base.packets.input.nodes;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import net.rustmc.cloud.base.communicate.CommunicatePacket;
import net.rustmc.cloud.base.communicate.PacketIdentifier;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 28.10.2022
 */
@Getter
@PacketIdentifier(identifier = 'r')
public class PacketInNodeMemory extends CommunicatePacket<PacketInNodeMemory> {

    public PacketInNodeMemory(int memory, int nodeKey) {
        this.memory = memory;
        this.nodeKey = nodeKey;
    }

    public PacketInNodeMemory() {
    }

    private int memory;
    private int nodeKey;

    @Override
    public void decode(ByteBuf buf) {
        this.memory = buf.readInt();
        this.nodeKey = buf.readInt();
    }

    @Override
    public void encode(ByteBuf buf) {
        buf.writeInt(this.memory);
        buf.writeInt(this.nodeKey);
    }
}
