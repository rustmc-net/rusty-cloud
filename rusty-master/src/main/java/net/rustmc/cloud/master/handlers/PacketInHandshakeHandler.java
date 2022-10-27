package net.rustmc.cloud.master.handlers;

import net.rustmc.cloud.api.events.node.CloudNodeConnectCompleteEvent;
import net.rustmc.cloud.api.events.node.CloudNodeConnectEvent;
import net.rustmc.cloud.base.common.Rust;
import net.rustmc.cloud.base.communicate.CommunicateChannelHandler;
import net.rustmc.cloud.base.communicate.ICommunicateChannel;
import net.rustmc.cloud.base.console.ICloudConsole;
import net.rustmc.cloud.base.packets.output.nodes.PacketOutNodeMemory;
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
            @SuppressWarnings("DataFlowIssue")
            @Override
            public void handle(PacketInHandshake packet, ICommunicateChannel channel) {
                if (RustCloud.getCloud().getNodeManager().containsNodeKey(packet.getNodeKey())) {
                    final var nodeObject = RustCloud.getCloud().getNodeManager().getNodeByNodeKey(packet.getNodeKey());
                    Rust.getInstance().getEventPerformer().perform(new CloudNodeConnectEvent(nodeObject));
                    if (!CloudNodeConnectEvent.flush()) {
                        RustCloud.getCloud().getChannelFlow().flush();
                        RustCloud.getCloud().getChannelFlow().remove(channel.getShortID());
                        RustCloud.getCloud().getCommunicateBaseChannel().dispatch(new PacketOutHandshake(), channel.getUniqueID());
                        RustCloud.getCloud().getCloudConsole().send("The node §a" +
                                nodeObject.getName() +
                                " §rhas connected to the server.");
                        RustCloud.getCloud().getCommunicateBaseChannel().dispatch(new PacketOutNodeMemory(nodeObject.getMaxMemory()), channel.getUniqueID());
                        Rust.getInstance().getEventPerformer().perform(new CloudNodeConnectCompleteEvent(nodeObject));
                    }
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
