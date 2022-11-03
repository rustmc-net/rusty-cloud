package net.rustmc.cloud.node.handlers;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.stream.ChunkedFile;
import net.rustmc.cloud.base.console.ICloudConsole;
import net.rustmc.cloud.node.RustCloud;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 03.11.2022
 */
public class ChannelInboundChunkHandler extends SimpleChannelInboundHandler<ChunkedFile> {

    protected static ChunkedFile latest;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ChunkedFile chunkedFile) throws Exception {
        RustCloud.getCloud().getCloudConsole().send("RECIEVED", ICloudConsole.Output.WARN);
        latest = chunkedFile;
    }

    public static ChunkedFile next() {
        final var out = latest;
        latest = null;
        return out;
    }

}
