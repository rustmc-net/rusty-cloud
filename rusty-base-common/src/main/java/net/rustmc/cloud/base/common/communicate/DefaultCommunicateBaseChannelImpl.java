package net.rustmc.cloud.base.common.communicate;

import io.netty.channel.*;
import io.netty.handler.stream.ChunkedFile;
import lombok.SneakyThrows;
import net.rustmc.cloud.base.common.Rust;
import net.rustmc.cloud.base.communicate.CommunicatePacket;
import net.rustmc.cloud.base.communicate.ICommunicateBaseChannel;
import net.rustmc.cloud.base.communicate.ICommunicateBaseHandlerPool;

import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.net.SocketAddress;
import java.nio.channels.FileChannel;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
public class DefaultCommunicateBaseChannelImpl implements ICommunicateBaseChannel {

    private final Channel core;
    private final ICommunicateBaseHandlerPool communicateBaseHandlerPool;
    private final boolean client;
    private final int localID;

    public DefaultCommunicateBaseChannelImpl(Channel core, ICommunicateBaseHandlerPool communicateBaseHandlerPool, boolean client, int localID) {
        this.core = core;
        this.communicateBaseHandlerPool = communicateBaseHandlerPool;
        this.client = client;
        this.localID = localID;
    }

    @Override
    public <T extends CommunicatePacket<?>> void dispatch(T packet) {
        if (this.isClient()) {
            this.core.writeAndFlush(packet).addListener(new ChannelFutureListener() {
                public void operationComplete(ChannelFuture channelFuture) {
                    if (!channelFuture.isSuccess()) {
                        channelFuture.cause().printStackTrace();
                    }
                }
            });
        } else {
            for (Channel channel : Rust.getInstance().getChannelFactory().getGroups().get(this.localID)) {
                channel.writeAndFlush(packet).addListener(new ChannelFutureListener() {
                    public void operationComplete(ChannelFuture channelFuture) {
                        if (!channelFuture.isSuccess()) {
                            channelFuture.cause().printStackTrace();
                        }
                    }
                });
            }
        }
    }

    @Override
    public ICommunicateBaseHandlerPool getBaseHandlerPool() {
        return this.communicateBaseHandlerPool;
    }

    @Override
    public <T extends CommunicatePacket<?>> void dispatch(T packet, String uniqueID) {
        if (this.isClient())
            throw new UnsupportedOperationException("Only the server can send to certain client packets.");
        for (Channel channel : Rust.getInstance().getChannelFactory().getGroups().get(this.localID)) {
            if (channel.id().asLongText().equals(uniqueID)) {
                channel.writeAndFlush(packet).addListener(new ChannelFutureListener() {
                    public void operationComplete(ChannelFuture channelFuture) {
                        if (!channelFuture.isSuccess()) {
                            channelFuture.cause().printStackTrace();
                        }
                    }
                });
            }
        }
    }

    @Override
    public boolean isClient() {
        return this.client;
    }

    @Override
    public Channel origin() {
        return this.core;
    }

    @Override
    public void dispatch(Object o) {
        this.core.writeAndFlush(o);
    }

    @SneakyThrows
    @SuppressWarnings({"DuplicatedCode", "resource"})
    @Override
    public void dispatch(File file, String uniqueID) {
        if (this.isClient())
            throw new UnsupportedOperationException("Only the server can send to certain client packets.");
        FileInputStream in = new FileInputStream(file);
        FileRegion region = new DefaultFileRegion(in.getChannel(), 0, file.length());
        for (Channel channel : Rust.getInstance().getChannelFactory().getGroups().get(this.localID)) {
            if (channel.id().asLongText().equals(uniqueID)) {
                channel.writeAndFlush(region).addListener(new ChannelFutureListener() {
                    public void operationComplete(ChannelFuture channelFuture) {
                        if (!channelFuture.isSuccess()) {
                            channelFuture.cause().printStackTrace();
                        }
                    }
                });
            }
        }
    }

    @Override
    public void decline() {
        this.core.close();
    }

    @Override
    public SocketAddress getAddress() {
        return this.core.localAddress();
    }

    @Override
    public String getUniqueID() {
        return this.core.id().asLongText();
    }

    @Override
    public String getShortID() {
        return this.core.id().asShortText();
    }
}
