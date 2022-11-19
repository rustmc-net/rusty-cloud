package net.rustmc.cloud.master.handlers;

import io.netty.channel.ChannelHandlerContext;
import net.rustmc.cloud.base.common.Rust;
import net.rustmc.cloud.base.common.communicate.CommunicationFuture;
import net.rustmc.cloud.base.communicate.CommunicatePacket;
import net.rustmc.cloud.base.packets.input.handshake.PacketInHandshake;
import net.rustmc.cloud.base.packets.output.handshake.PacketOutHandshake;
import net.rustmc.cloud.master.RustCloud;

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
            @Override
            public void accept(ChannelHandlerContext channelHandlerContext) {
                final CommunicationFuture<PacketInHandshake> handshakeCommunicationFuture = new CommunicationFuture<>(new PacketOutHandshake(), channelHandlerContext.channel().id().asLongText(), new BiConsumer<ChannelHandlerContext, CommunicatePacket<?>>() {
                    @Override
                    public void accept(ChannelHandlerContext channelHandlerContext, CommunicatePacket<?> communicatePacket) {
                        System.out.println("Hello World");
                        final var income = (PacketInHandshake) communicatePacket;
                        final var node = RustCloud.getCloud().getOfflineNodeTerminal().getOfflineNodeByNodeKey(income.getNodeKey());
                        if (node != null) {
                            RustCloud.getCloud().getCommunicateChannel().dispatch(new PacketOutHandshake(), channelHandlerContext.channel().id().asLongText());
                            RustCloud.getCloud().getCloudConsole().send("the §a" + node.configuration().getName() + " §rhas connected to the server.");
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
