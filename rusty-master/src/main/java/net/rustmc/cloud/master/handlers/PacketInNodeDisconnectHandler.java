package net.rustmc.cloud.master.handlers;

import net.rustmc.cloud.base.communicate.CommunicateChannelHandler;
import net.rustmc.cloud.base.communicate.ICommunicateChannel;
import net.rustmc.cloud.base.packets.input.handshake.PacketInDisconnect;
import net.rustmc.cloud.master.RustCloud;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 21.11.2022
 */
public class PacketInNodeDisconnectHandler {

    public PacketInNodeDisconnectHandler() {
        RustCloud.getCloud().getCommunicateChannel().getBaseHandlerPool().subscribe(PacketInDisconnect.class, new CommunicateChannelHandler<PacketInDisconnect>() {
            @Override
            public void handle(PacketInDisconnect packet, ICommunicateChannel channel) {
                final var name = RustCloud.getCloud()
                        .getOnlineNodeTerminal()
                        .getByUniqueID(channel.getUniqueID())
                        .offline()
                        .configuration()
                        .getName();
                RustCloud.getCloud()
                        .getOnlineNodeTerminal()
                        .remove(name);
                RustCloud.getCloud().getCloudConsole().send("the node §a" + name + " §rhas been removed.");
            }
        });
    }

}
