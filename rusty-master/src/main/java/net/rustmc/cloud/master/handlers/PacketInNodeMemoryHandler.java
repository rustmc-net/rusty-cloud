package net.rustmc.cloud.master.handlers;

import net.rustmc.cloud.base.communicate.CommunicateChannelHandler;
import net.rustmc.cloud.base.communicate.ICommunicateChannel;
import net.rustmc.cloud.base.console.ICloudConsole;
import net.rustmc.cloud.base.packets.input.nodes.PacketInNodeMemory;
import net.rustmc.cloud.master.RustCloud;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 28.10.2022
 */
public class PacketInNodeMemoryHandler {

    public PacketInNodeMemoryHandler() {
        RustCloud.getCloud().getCommunicateBaseChannel().getBaseHandlerPool().subscribe(PacketInNodeMemory.class, new CommunicateChannelHandler<PacketInNodeMemory>() {
            @SuppressWarnings("DataFlowIssue")
            @Override
            public void handle(PacketInNodeMemory packet, ICommunicateChannel channel) {
                final var node = RustCloud.getCloud()
                        .getNodeManager()
                        .getNodeByNodeAddress(channel.getAddress()
                                .toString()
                                .replace("/", "")
                                .substring(0, channel
                                        .getAddress()
                                        .toString()
                                        .indexOf(':')));
                RustCloud.getCloud()
                        .getOpenedNodePool()
                        .getByNodeKey(
                                node.getNodeKey())
                        .setFreeMemory(packet.getMemory());
                if (RustCloud.getCloud().getConfiguration().getMinRam() <= packet.getMemory()) {
                    //TODO: Handle groups for node
                    RustCloud.getCloud().getCloudConsole().send("The node §a" + node.getName() + " §rcan be allocated " + RustCloud.getCloud()
                            .getOpenedNodePool()
                            .getByNodeKey(node.getNodeKey())
                            .getFreeMemory() + " MB of memory.");
                } else {
                    RustCloud
                            .getCloud()
                            .getCloudConsole()
                            .send("The allocation of memory fills the capacity of the node: §e" + node.getName(), ICloudConsole.Output.WARN);
                }
            }
        });
    }
}
