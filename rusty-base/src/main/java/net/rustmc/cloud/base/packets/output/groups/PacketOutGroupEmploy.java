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
@PacketIdentifier(identifier = 'x')
public class PacketOutGroupEmploy extends CommunicatePacket<PacketOutGroupEmploy> {

    private String name;
    private int memory;
    private int maxPlayers;
    private int maxServers;
    private int minServers;
    private int version;

    public PacketOutGroupEmploy(String name, int memory, int maxPlayers, int maxServers, int minServers, int version) {
        this.name = name;
        this.memory = memory;
        this.maxPlayers = maxPlayers;
        this.maxServers = maxServers;
        this.minServers = minServers;
        this.version = version;
    }

    public PacketOutGroupEmploy() {
    }

    @Override
    public void decode(ByteBuf buf) {
        this.name = ByteBufHelper.readString(buf);
        this.memory = buf.readInt();
        this.maxPlayers = buf.readInt();
        this.maxServers = buf.readInt();
        this.minServers = buf.readInt();
        this.version = buf.readInt();
    }

    @Override
    public void encode(ByteBuf buf) {
        ByteBufHelper.write(name, buf);
        buf.writeInt(memory);
        buf.writeInt(maxPlayers);
        buf.writeInt(maxServers);
        buf.writeInt(minServers);
        buf.writeInt(version);
    }

}
