package net.rustmc.cloud.base.packets.input.nodes;

import io.netty.buffer.ByteBuf;
import lombok.Getter;
import net.rustmc.cloud.base.communicate.CommunicatePacket;
import net.rustmc.cloud.base.communicate.PacketIdentifier;
import net.rustmc.cloud.base.util.ByteBufHelper;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 29.10.2022
 */
@Getter
@PacketIdentifier(identifier = 't')
public class PacketInNodeCacheGroups extends CommunicatePacket<PacketInNodeCacheGroups> {

    private String[] groups;

    public PacketInNodeCacheGroups(String[] groups) {
        this.groups = groups;
    }

    @Override
    public void decode(ByteBuf buf) {
        this.groups = ByteBufHelper.readDynamicArray(buf);
    }

    @Override
    public void encode(ByteBuf buf) {
        ByteBufHelper.writeDynamicArray(this.groups, buf);
    }

}
