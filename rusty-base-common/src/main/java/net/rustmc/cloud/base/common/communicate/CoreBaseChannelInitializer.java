package net.rustmc.cloud.base.common.communicate;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import net.rustmc.cloud.base.common.communicate.codec.CoreChannelDecodeHandler;
import net.rustmc.cloud.base.common.communicate.codec.CoreChannelEncodeHandler;
import net.rustmc.cloud.base.communicate.ICommunicateBaseHandlerPool;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
public class CoreBaseChannelInitializer extends ChannelInitializer<Channel> {

    private final ICommunicateBaseHandlerPool handlerPool;
    private final int localID;

    public CoreBaseChannelInitializer(ICommunicateBaseHandlerPool handlerPool, int localID) {
        this.handlerPool = handlerPool;
        this.localID = localID;
    }

    @Override
    protected void initChannel(Channel channel) {
        channel.pipeline()
                .addLast(new CoreChannelDecodeHandler())
                .addLast(new CoreChannelEncodeHandler())
                .addLast(new CoreChannelInboundHandler(handlerPool, localID));
    }
}
