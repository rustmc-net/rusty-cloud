package net.rustmc.cloud.base.packets.input.groups;

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
@PacketIdentifier(identifier = 'a')
public final class PacketInGroupInfoRequest extends CommunicatePacket<PacketInGroupInfoRequest> {

    private String name;

    public PacketInGroupInfoRequest(String name) {
        this.name = name;
    }

    public PacketInGroupInfoRequest() {
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
