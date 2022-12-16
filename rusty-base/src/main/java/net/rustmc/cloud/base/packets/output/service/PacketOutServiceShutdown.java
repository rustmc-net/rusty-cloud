package net.rustmc.cloud.base.packets.output.service;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import net.rustmc.cloud.base.communicate.CommunicatePacket;
import net.rustmc.cloud.base.communicate.PacketIdentifier;
import net.rustmc.cloud.base.util.ByteBufHelper;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 16.12.2022
 */
@Getter
@PacketIdentifier(identifier = 'y')
public class PacketOutServiceShutdown extends CommunicatePacket<PacketOutServiceShutdown> {

    private String name;

    public PacketOutServiceShutdown(String name) {
        this.name = name;
    }

    public PacketOutServiceShutdown() {
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
