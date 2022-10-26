package net.rustmc.cloud.node.handlers;

import net.rustmc.cloud.base.communicate.CommunicateChannelHandler;
import net.rustmc.cloud.base.communicate.ICommunicateChannel;
import net.rustmc.cloud.base.packets.output.handshake.PacketOutHandshake;
import net.rustmc.cloud.node.RustCloud;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 26.10.2022
 */
public class PacketOutHandshakeHandler {

    public PacketOutHandshakeHandler() {
        RustCloud.getCloud().getCommunicateBaseChannel().getBaseHandlerPool().subscribe(PacketOutHandshake.class, new CommunicateChannelHandler<PacketOutHandshake>() {
            @Override
            public void handle(PacketOutHandshake packet, ICommunicateChannel channel) {
                RustCloud.getCloud().getCloudConsole().send("A connection could be established successfully. -> §a" + channel.getAddress());
            }
        });
    }

}
