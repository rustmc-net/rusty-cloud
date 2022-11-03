package net.rustmc.cloud.base.common.communicate.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.ByteProcessor;
import lombok.SneakyThrows;
import net.rustmc.cloud.base.common.Rust;
import net.rustmc.cloud.base.communicate.CommunicatePacket;
import net.rustmc.cloud.base.packets.output.groups.PacketOutGroupEmploy;
import net.rustmc.cloud.base.util.operations.UnsafeInstanceOperations;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
public class CoreChannelDecodeHandler extends ByteToMessageDecoder {

    boolean pause = false;
    boolean start;

    @SneakyThrows
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf buf, List<Object> output) {
        if (!pause) {
            final Class<? extends CommunicatePacket<?>> scaffold = Rust.getInstance().getCommunicatePacketPool().of(buf.readChar());
            final CommunicatePacket<?> packet = scaffold.getConstructor().newInstance();
            if (packet.getClass().getSimpleName().equals(PacketOutGroupEmploy.class.getSimpleName())) {
                pause = true;
            } else {
                packet.decode(buf);
                output.add(packet);
            }
        } else {
            if (!this.start) {
                this.start = true;
                Rust.getInstance().getAsynchronousExecutor().schedule(new Runnable() {
                    @SneakyThrows
                    @Override
                    public void run() {
                        flush();
                        final var out = PacketOutGroupEmploy.class.getConstructor().newInstance();
                        out.decode(buf);
                        System.out.println(out.getName());
                        output.add(out);
                    }
                }, 100, TimeUnit.MILLISECONDS);
            }
        }
    }

    protected void flush() {
        this.pause = false;
        this.start = false;
    }

}
