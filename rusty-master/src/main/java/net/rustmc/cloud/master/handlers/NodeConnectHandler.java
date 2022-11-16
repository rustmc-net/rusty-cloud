package net.rustmc.cloud.master.handlers;

import io.netty.channel.ChannelHandlerContext;
import net.rustmc.cloud.master.RustCloud;

import java.util.function.Consumer;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 16.11.2022
 */
public class NodeConnectHandler {

    public NodeConnectHandler() {
        RustCloud.getCloud().getCommunicateChannel().getBaseHandlerPool().subscribeBootHandler(new Consumer<ChannelHandlerContext>() {
            @Override
            public void accept(ChannelHandlerContext channelHandlerContext) {

            }
        });
    }

}
