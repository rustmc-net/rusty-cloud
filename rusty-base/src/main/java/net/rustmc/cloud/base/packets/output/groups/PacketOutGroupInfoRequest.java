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
 * @since 28.10.2022
 */
@Getter
@PacketIdentifier(identifier = 'w')
public final class PacketOutGroupInfoRequest extends CommunicatePacket<PacketOutGroupInfoRequest> {

    private String name;
    private int version;
    private int maxPlayers;
    private int memory;
    private int maxServers;
    private int minServer;

    @Override
    public void decode(ByteBuf buf) {
        ByteBufHelper.write(this.name, buf);
        buf.writeInt(this.version);
        buf.writeInt(this.maxPlayers);
        buf.writeInt(this.memory);
        buf.writeInt(this.maxServers);
        buf.writeInt(this.minServer);
    }

    @Override
    public void encode(ByteBuf buf) {
        this.name = ByteBufHelper.readString(buf);
        this.version = buf.readInt();
        this.maxPlayers = buf.readInt();
        this.memory = buf.readInt();
        this.maxServers = buf.readInt();
        this.minServer = buf.readInt();
    }

}
