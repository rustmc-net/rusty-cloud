package net.rustmc.cloud.master.common.channels;

import io.netty.channel.Channel;
import net.rustmc.cloud.base.packets.output.handshake.PacketOutChannelOverFlow;
import net.rustmc.cloud.master.RustCloud;
import net.rustmc.cloud.master.handlers.channel.IChannelFlow;

import java.util.LinkedList;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 25.10.2022
 */
public final class ChannelFlowImpl implements IChannelFlow {

    private Channel queue;
    private final LinkedList<String> inClosure = new LinkedList<>();

    @Override
    public void queue(Channel channel) {
        if (RustCloud.getCloud().getConfiguration().isChainConnect()) {
            if (queue != null) {
                try {
                    channel.write(new PacketOutChannelOverFlow());
                    channel.flush();
                    channel.eventLoop().shutdownGracefully();
                    channel.close();
                } catch (RuntimeException e) {
                    throw new RuntimeException(e);
                }
            } else {
                this.queue = channel;
                this.inClosure.add(channel.id().asShortText());
            }
        } else {
            this.queue = channel;
            this.inClosure.add(channel.id().asShortText());
        }
    }

    @Override
    public void flush() {
        this.queue = null;
    }

    @Override
    public Channel getChannel() {
        return this.queue;
    }

    @Override
    public boolean contains(String shortIdentifier) {
        final var result = this.inClosure.contains(shortIdentifier);
        if (result) this.inClosure.remove(shortIdentifier);
        return result;
    }

}
