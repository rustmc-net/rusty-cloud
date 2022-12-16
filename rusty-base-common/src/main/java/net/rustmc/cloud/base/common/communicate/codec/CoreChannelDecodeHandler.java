package net.rustmc.cloud.base.common.communicate.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import net.rustmc.cloud.base.common.Rust;
import net.rustmc.cloud.base.communicate.CommunicatePacket;
import net.rustmc.cloud.base.packets.PacketPauseCodec;
import net.rustmc.cloud.base.util.operations.UnsafeInstanceOperations;

import java.util.List;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
public class CoreChannelDecodeHandler extends ByteToMessageDecoder {

    boolean pause = false;

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf buf, List<Object> output) {
        if (!pause) {
            final Class<? extends CommunicatePacket<?>> scaffold = Rust.getInstance().getCommunicatePacketPool().of(buf.getChar(0));
            if (scaffold == null) {
                output.add(buf);
                return;
            } else {
                buf.readChar();
            }
            final CommunicatePacket<?> packet = UnsafeInstanceOperations.construct(scaffold);
            if (packet.getClass().equals(PacketPauseCodec.class)) {
                pause = true;
                return;
            }
            packet.decode(buf);
            output.add(packet);
        } else {
            output.add(buf);
            pause = false;
        }
    }

}
