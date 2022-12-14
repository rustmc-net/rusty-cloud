package net.rustmc.cloud.base.packets.output.transfer;

import io.netty.buffer.ByteBuf;
import net.rustmc.cloud.base.communicate.CommunicatePacket;
import net.rustmc.cloud.base.communicate.PacketIdentifier;
import net.rustmc.cloud.base.util.ByteBufHelper;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 13.12.2022
 */
@PacketIdentifier(identifier = 'n')
public class PacketOutGroupTransfer extends CommunicatePacket<PacketOutGroupTransfer> {

    private String name;

    public PacketOutGroupTransfer() {
    }

    public PacketOutGroupTransfer(String name) {
        this.name = name;
    }

    @Override
    public void decode(ByteBuf buf) {
        this.name = ByteBufHelper.readString(buf);
    }

    @Override
    public void encode(ByteBuf buf) {
        ByteBufHelper.write(this.name, buf);
    }
}
