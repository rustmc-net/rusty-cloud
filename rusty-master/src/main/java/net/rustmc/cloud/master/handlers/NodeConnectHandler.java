package net.rustmc.cloud.master.handlers;

import io.netty.channel.ChannelHandlerContext;
import net.rustmc.cloud.base.common.Rust;
import net.rustmc.cloud.base.common.communicate.CommunicationFuturePromise;
import net.rustmc.cloud.base.common.communicate.DefaultChannelImpl;
import net.rustmc.cloud.base.communicate.CommunicatePacket;
import net.rustmc.cloud.base.packets.input.handshake.PacketInHandshake;
import net.rustmc.cloud.base.packets.output.handshake.PacketOutHandshake;
import net.rustmc.cloud.master.RustCloud;
import net.rustmc.cloud.master.groups.ICloudGroup;
import net.rustmc.cloud.master.nodes.IOnlineNode;

import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 16.11.2022
 */
public class NodeConnectHandler {

    boolean timeout = false;

    public NodeConnectHandler() {

        RustCloud.getCloud().getCommunicateChannel().getBaseHandlerPool().subscribeBootHandler(new Consumer<ChannelHandlerContext>() {
            @SuppressWarnings("InstantiationOfUtilityClass")
            @Override
            public void accept(ChannelHandlerContext channelHandlerContext) {
                final CommunicationFuturePromise<PacketInHandshake> handshakeCommunicationFuturePromise = new CommunicationFuturePromise<>(new PacketOutHandshake(), channelHandlerContext.channel().id().asLongText(), new BiConsumer<ChannelHandlerContext, CommunicatePacket<?>>() {
                    @Override
                    public void accept(ChannelHandlerContext channelHandlerContext, CommunicatePacket<?> communicatePacket) {
                        final var income = (PacketInHandshake) communicatePacket;
                        final var node = RustCloud.getCloud().getOfflineNodeTerminal().getOfflineNodeByNodeKey(income.getNodeKey());
                        if (node != null) {
                            RustCloud.getCloud().getOnlineNodeTerminal().open(node, new DefaultChannelImpl(channelHandlerContext.channel()));
                            RustCloud.getCloud().getCommunicateChannel().dispatch(new PacketOutHandshake(), channelHandlerContext.channel().id().asLongText());
                            RustCloud.getCloud().getCloudConsole().send("the §a" + node.configuration().getName() + " §rhas connected to the server.");
                            if (income.getGroups().length != 0) {
                                RustCloud.getCloud().getOnlineNodeTerminal().getByName(node.configuration().getName()).store(IOnlineNode.NodeRequest.REMOTE_GROUPS.name(), income.getGroups());
                                RustCloud.getCloud().getCloudConsole().send("the node does §rcurrently have §a" + income.getGroups().length + " §rgroups stored.");
                                for (ICloudGroup group : node.getAllocatedGroups()) {
                                    RustCloud.getCloud().getGroupTerminal().requestTransfer(group);
                                }
                            } else RustCloud.getCloud().getCloudConsole().send("the node does §enot §rcurrently have any groups stored.");
                        }
                        timeout = true;
                    }
                });
                Rust.getInstance().getAsynchronousExecutor().schedule(new Runnable() {
                    @Override
                    public void run() {
                        if (!timeout) {
                            RustCloud.getCloud().getCloudConsole().send("a not recognized §eclient §rhas been detected (§e" + channelHandlerContext.channel().remoteAddress().toString() + "§r)");
                            channelHandlerContext.channel().close();
                        }
                    }
                }, 1000, TimeUnit.MILLISECONDS);
            }
        });
    }

}
