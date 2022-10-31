package net.rustmc.cloud.base.packets.output.groups;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import net.rustmc.cloud.base.communicate.CommunicatePacket;
import net.rustmc.cloud.base.communicate.PacketIdentifier;
import net.rustmc.cloud.base.util.ByteBufHelper;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 31.10.2022
 */
@Getter
@PacketIdentifier(identifier = 'u')
public class PacketOutGroupEmploy extends CommunicatePacket<PacketOutGroupEmploy> {

    private Byte[] directoryContent;
    private String name;
    private int memory;

    public PacketOutGroupEmploy(Byte[] directoryContent, String name, int memory) {
        this.directoryContent = directoryContent;
        this.name = name;
        this.memory = memory;
    }

    @Override
    public void decode(ByteBuf buf) {
        this.name = ByteBufHelper.readString(buf);
        this.memory = buf.readInt();
        this.directoryContent = ByteBufHelper.readDynamicByteArray(buf);
    }

    @Override
    public void encode(ByteBuf buf) {
        ByteBufHelper.write(name, buf);
        buf.writeInt(memory);
        ByteBufHelper.writeDynamicByteArray(this.directoryContent, buf);
    }

}
