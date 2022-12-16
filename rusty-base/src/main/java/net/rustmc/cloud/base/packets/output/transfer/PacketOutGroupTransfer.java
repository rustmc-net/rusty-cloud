package net.rustmc.cloud.base.packets.output.transfer;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import net.rustmc.cloud.base.communicate.CommunicatePacket;
import net.rustmc.cloud.base.communicate.PacketIdentifier;
import net.rustmc.cloud.base.objects.PrimitiveGroup;
import net.rustmc.cloud.base.util.ByteBufHelper;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 13.12.2022
 */
@Getter
@PacketIdentifier(identifier = 'n')
public class PacketOutGroupTransfer extends CommunicatePacket<PacketOutGroupTransfer> {

    private PrimitiveGroup primitiveGroup;

    public PacketOutGroupTransfer() {
    }

    public PacketOutGroupTransfer(String name, boolean template) {
        this.primitiveGroup = PrimitiveGroup.group(name, template);
    }

    @Override
    public void decode(ByteBuf buf) {
        this.primitiveGroup = PrimitiveGroup.group(ByteBufHelper.readString(buf), buf.readBoolean());
    }

    @Override
    public void encode(ByteBuf buf) {
        ByteBufHelper.write(this.primitiveGroup.name, buf);
        buf.writeBoolean(this.primitiveGroup.template);
    }
}
