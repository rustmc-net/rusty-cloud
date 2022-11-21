package net.rustmc.cloud.base.common.communicate;

import io.netty.channel.ChannelHandlerContext;
import net.rustmc.cloud.base.common.Rust;
import net.rustmc.cloud.base.communicate.CommunicatePacket;
import net.rustmc.cloud.base.communicate.ICommunicateBaseChannel;

import java.util.function.BiConsumer;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 16.11.2022
 */
public class CommunicationFuturePromise<T extends CommunicatePacket<?>> {

    private static ICommunicateBaseChannel baseChannel;
    private static BiConsumer<ChannelHandlerContext, CommunicatePacket<?>> handler;
    private static String uniqueID;

    public static void subscribe(ICommunicateBaseChannel communicateBaseChannel) {
        baseChannel = communicateBaseChannel;
        communicateBaseChannel.getBaseHandlerPool().subscribe(new BiConsumer<ChannelHandlerContext, Object>() {
            @Override
            public void accept(ChannelHandlerContext channelHandlerContext, Object o) {
                if (channelHandlerContext.channel().id().asLongText().equals(uniqueID)) {
                    handler.accept(channelHandlerContext, (CommunicatePacket<?>) o);
                    flush();
                }
            }
        });
    }

    public CommunicationFuturePromise(final CommunicatePacket<?> packet, String uniqueID, BiConsumer<ChannelHandlerContext, CommunicatePacket<?>> handler) {
        CommunicationFuturePromise.uniqueID = uniqueID;
        CommunicationFuturePromise.handler = handler;
        baseChannel.dispatch(packet, uniqueID);
    }

    private static void flush() {
        uniqueID = null;
        handler = null;
    }

}
