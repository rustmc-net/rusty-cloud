package net.rustmc.cloud.master.handlers;

import net.rustmc.cloud.base.common.Rust;
import net.rustmc.cloud.base.communicate.CommunicateChannelHandler;
import net.rustmc.cloud.base.communicate.ICommunicateChannel;
import net.rustmc.cloud.base.console.ICloudConsole;
import net.rustmc.cloud.base.packets.input.handshake.PacketInHandshake;
import net.rustmc.cloud.base.packets.output.handshake.PacketOutHandshake;
import net.rustmc.cloud.master.RustCloud;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 25.10.2022
 */
public class PacketInHandshakeHandler {

    public PacketInHandshakeHandler() {
        RustCloud.getCloud().getCommunicateBaseChannel().getBaseHandlerPool().subscribe(PacketInHandshake.class, new CommunicateChannelHandler<PacketInHandshake>() {
            @Override
            public void handle(PacketInHandshake packet, ICommunicateChannel channel) {
                if (RustCloud.getCloud().getNodeManager().containsNodeKey(packet.getNodeKey())) {
                    RustCloud.getCloud().getChannelFlow().flush();
                    RustCloud.getCloud().getChannelFlow().remove(channel.getShortID());
                    RustCloud.getCloud().getCommunicateBaseChannel().dispatch(new PacketOutHandshake(), channel.getUniqueID());
                    RustCloud.getCloud().getCloudConsole().send("The node §a" +
                            RustCloud
                                    .getCloud()
                                    .getNodeManager()
                                    .getNameOfNodeKey(packet.getNodeKey()) +
                            " §rhas connected to the server.");
                } else {
                    RustCloud.getCloud()
                            .getCloudConsole()
                            .send("A client with an invalid NodeKey was captured. (§e" +
                                            channel
                                            .getAddress()
                                            .toString() + "§r)",
                                    ICloudConsole.Output.WARN);
                    RustCloud
                            .getCloud()
                            .getCloudConsole()
                            .send("Please check whether the key of the NodeClient matches the NodeKey entered in the server via .", ICloudConsole.Output.WARN);
                }
            }
        });
    }

}
