package net.rustmc.cloud.node.handlers;

import net.rustmc.cloud.base.communicate.CommunicateChannelHandler;
import net.rustmc.cloud.base.communicate.ICommunicateChannel;
import net.rustmc.cloud.base.packets.output.nodes.PacketOutNodeMemory;
import net.rustmc.cloud.node.RustCloud;
import net.rustmc.cloud.node.common.DefaultMemoryImpl;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 28.10.2022
 */
public class PacketOutNodeMemoryHandler {

    public PacketOutNodeMemoryHandler() {
        RustCloud.getCloud().getCommunicateBaseChannel().getBaseHandlerPool().subscribe(PacketOutNodeMemory.class, new CommunicateChannelHandler<PacketOutNodeMemory>() {
            @Override
            public void handle(PacketOutNodeMemory packet, ICommunicateChannel channel) {
                DefaultMemoryImpl.insert(packet.getMemory());
            }
        });
    }

}
