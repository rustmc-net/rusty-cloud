package net.rustmc.cloud.master.handlers;

import io.netty.channel.ChannelHandlerContext;
import net.rustmc.cloud.master.RustCloud;

import java.util.function.Consumer;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 21.11.2022
 */
public class NodeDisconnectHandler {

    public NodeDisconnectHandler() {
        RustCloud.getCloud().getCommunicateChannel().getBaseHandlerPool().subscribeCloseHandler(new Consumer<ChannelHandlerContext>() {
            @Override
            public void accept(ChannelHandlerContext channelHandlerContext) {
                final var name = RustCloud.getCloud()
                        .getOnlineNodeTerminal()
                        .getByUniqueID(channelHandlerContext.channel().id().asLongText())
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
