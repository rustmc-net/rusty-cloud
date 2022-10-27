package net.rustmc.cloud.base.packets.output.nodes;

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
@PacketIdentifier(identifier = 'm')
public final class PacketOutNodeMemory extends CommunicatePacket<PacketOutNodeMemory> {

    private int memory;

    public PacketOutNodeMemory(int memory) {
        this.memory = memory;
    }

    public PacketOutNodeMemory() {
    }

    @Override
    public void decode(ByteBuf buf) {
        buf.writeInt(this.memory);
    }

    @Override
    public void encode(ByteBuf buf) {
        this.memory = buf.readInt();
    }
}
