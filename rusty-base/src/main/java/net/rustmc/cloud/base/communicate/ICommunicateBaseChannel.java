package net.rustmc.cloud.base.communicate;

import io.netty.channel.Channel;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
public interface ICommunicateBaseChannel extends ICommunicateChannel {

    public <T extends CommunicatePacket<?>> void dispatch(final T packet);

    public ICommunicateBaseHandlerPool getBaseHandlerPool();

    public <T extends CommunicatePacket<?>> void dispatch(final T packet, final String uniqueID);

    public boolean isClient();

    public Channel origin();

    public void dispatch(Object o);

}
