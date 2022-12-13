package net.rustmc.cloud.base.common.communicate.files;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 13.12.2022
 */
public class SimpleChunkedFileHandler extends ChannelInboundHandlerAdapter {

    private static byte[] content = null;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object buf) throws Exception {
        if (buf instanceof ByteBuf) {
            content = ((ByteBuf) buf).array();
        }
    }

    public static byte[] read() {
        final var content = SimpleChunkedFileHandler.content;
        SimpleChunkedFileHandler.content = null;
        return content;
    }

}
