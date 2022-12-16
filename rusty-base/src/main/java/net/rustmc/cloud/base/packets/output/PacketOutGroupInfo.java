package net.rustmc.cloud.base.packets.output;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import net.rustmc.cloud.base.communicate.CommunicatePacket;
import net.rustmc.cloud.base.communicate.PacketIdentifier;
import net.rustmc.cloud.base.objects.SimpleCloudGroup;
import net.rustmc.cloud.base.util.ByteBufHelper;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 25.10.2022
 */
@Getter
@PacketIdentifier(identifier = 'h')
public final class PacketOutGroupInfo extends CommunicatePacket<PacketOutGroupInfo> {

    private SimpleCloudGroup cloudGroup;

    public PacketOutGroupInfo() {
    }

    public PacketOutGroupInfo(SimpleCloudGroup cloudGroup) {
        this.cloudGroup = cloudGroup;
    }

    @Override
    public void decode(ByteBuf buf) {
        final var name = ByteBufHelper.readString(buf);
        final var proxy = buf.readBoolean();
        final var version = buf.readInt();
        final var maxPlayersPer = buf.readInt();
        final var percent = buf.readInt();
        final var maxServers = buf.readInt();
        final var minServers = buf.readInt();
        final var priority = buf.readInt();
        final var permission = ByteBufHelper.readString(buf);
        final int memory = buf.readInt();
        final var allocatedNode = ByteBufHelper.readString(buf);
        final boolean template = buf.readBoolean();
        this.cloudGroup = new SimpleCloudGroup(
                name,
                proxy,
                version,
                maxPlayersPer,
                percent,
                maxServers,
                minServers,
                priority,
                permission,
                memory,
                allocatedNode,
                template
        );
    }

    @Override
    public void encode(ByteBuf buf) {
        ByteBufHelper.write(this.cloudGroup.getName(), buf);
        buf.writeBoolean(this.cloudGroup.isProxy());
        buf.writeInt(this.cloudGroup.getVersion());
        buf.writeInt(this.cloudGroup.getMaxPlayersPer());
        buf.writeInt(this.cloudGroup.getPercent());
        buf.writeInt(this.cloudGroup.getMaxServers());
        buf.writeInt(this.cloudGroup.getMinServers());
        buf.writeInt(this.cloudGroup.getPriority());
        ByteBufHelper.write(this.cloudGroup.getPermission(), buf);
        buf.writeInt(this.cloudGroup.getMemory());
        ByteBufHelper.write("0", buf);
        buf.writeBoolean(this.cloudGroup.isTemplate());
    }

}
