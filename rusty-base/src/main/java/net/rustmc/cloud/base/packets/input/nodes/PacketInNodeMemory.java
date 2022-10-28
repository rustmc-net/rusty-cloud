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

    public PacketInNodeMemory(int memory) {
        this.memory = memory;
    }

    public PacketInNodeMemory() {
    }

    private int memory;

    @Override
    public void decode(ByteBuf buf) {
        this.memory = buf.readInt();
    }

    @Override
    public void encode(ByteBuf buf) {
        buf.writeInt(this.memory);
    }
}
