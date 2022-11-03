package net.rustmc.cloud.base.common.communicate.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.rustmc.cloud.base.common.Rust;
import net.rustmc.cloud.base.communicate.CommunicatePacket;
import net.rustmc.cloud.base.packets.EmptyPacket;
import net.rustmc.cloud.base.packets.output.groups.PacketOutGroupEmploy;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
public class CoreChannelEncodeHandler extends MessageToByteEncoder<CommunicatePacket<?>> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, CommunicatePacket<?> input, ByteBuf output) {
        output.writeChar(Rust.getInstance().getCommunicatePacketPool().of(input.getClass()));
        input.encode(output);
    }

}
