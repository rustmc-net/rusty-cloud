package net.rustmc.cloud.base.common.communicate.files;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ByteProcessor;

import java.util.LinkedList;
import java.util.List;

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
            content = convert((ByteBuf) buf);
            System.out.println("new file");
        }
    }

    private byte[] convert(ByteBuf buf) {
        final byte[] bytes = new byte[buf.readableBytes()];
        for (int i = 0; i <= buf.readableBytes()-1; i++) {
            bytes[i] = buf.readByte();
        }
        return bytes;
    }

    public static byte[] read() {
        final var content = SimpleChunkedFileHandler.content;
        SimpleChunkedFileHandler.content = null;
        return content;
    }

}
