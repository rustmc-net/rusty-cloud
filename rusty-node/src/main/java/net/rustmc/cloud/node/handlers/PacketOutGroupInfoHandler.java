package net.rustmc.cloud.node.handlers;

import net.rustmc.cloud.base.communicate.CommunicateChannelHandler;
import net.rustmc.cloud.base.communicate.ICommunicateChannel;
import net.rustmc.cloud.base.packets.output.PacketOutGroupInfo;
import net.rustmc.cloud.node.RustCloud;
import net.rustmc.cloud.node.commons.groups.types.StaticGroupImpl;
import net.rustmc.cloud.node.commons.groups.types.TemplateGroupImpl;

public class PacketOutGroupInfoHandler {

    public PacketOutGroupInfoHandler() {
        RustCloud.getCloud().getCommunicateBaseChannel().getBaseHandlerPool().subscribe(PacketOutGroupInfo.class, new CommunicateChannelHandler<PacketOutGroupInfo>() {
            @Override
            public void handle(PacketOutGroupInfo packet, ICommunicateChannel channel) {
                RustCloud.getCloud()
                        .getOnlineGroupPool()
                        .register(packet.getCloudGroup().isTemplate() ?
                                new TemplateGroupImpl(packet.getCloudGroup()) :
                                new StaticGroupImpl(packet.getCloudGroup())
                        )
                ;
            }
        });
    }

}
