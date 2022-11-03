package net.rustmc.cloud.base.common.communicate.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import net.rustmc.cloud.base.common.Rust;
import net.rustmc.cloud.base.communicate.CommunicatePacket;
import net.rustmc.cloud.base.util.operations.UnsafeInstanceOperations;

import java.util.List;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
public class CoreChannelDecodeHandler extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf buf, List<Object> output) {
        final Class<? extends CommunicatePacket<?>> scaffold = Rust.getInstance().getCommunicatePacketPool().of(buf.readChar());
        final CommunicatePacket<?> packet = UnsafeInstanceOperations.construct(scaffold);
        packet.decode(buf);
        output.add(packet);
    }

}
