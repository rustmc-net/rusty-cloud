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
@PacketIdentifier(identifier = 'x')
public class PacketOutServiceCommand extends CommunicatePacket<PacketOutServiceCommand> {

    private String name;
    private String command;

    public PacketOutServiceCommand(String name) {
        this.name = name;
    }

    public PacketOutServiceCommand() {
    }

    @Override
    public void decode(ByteBuf buf) {
        this.name = ByteBufHelper.readString(buf);
        this.command = ByteBufHelper.readString(buf);
    }

    @Override
    public void encode(ByteBuf buf) {
        ByteBufHelper.write(this.name, buf);
        ByteBufHelper.write(this.command, buf);
    }

}
