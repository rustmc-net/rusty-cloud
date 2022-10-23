package net.rustmc.cloud.base.common.communicate;

import io.netty.channel.Channel;
import net.rustmc.cloud.base.communicate.ICommunicateChannel;

import java.net.SocketAddress;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
public class DefaultChannelImpl implements ICommunicateChannel {

    private final Channel core;

    private DefaultChannelImpl(Channel core) {
        this.core = core;
    }

    public static ICommunicateChannel newChannel(Channel core) {
        return new DefaultChannelImpl(core);
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
}
