package net.rustmc.cloud.master.handlers.channel;

import io.netty.channel.Channel;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 25.10.2022
 */
public interface IChannelFlow {

    public void queue(final Channel channel);

    public void flush();

    public Channel getChannel();

    public boolean contains(final String shortIdentifier);

}
