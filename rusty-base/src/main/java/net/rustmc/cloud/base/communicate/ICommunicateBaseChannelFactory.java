package net.rustmc.cloud.base.communicate;

import io.netty.channel.group.ChannelGroup;

import java.util.LinkedHashMap;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
public interface ICommunicateBaseChannelFactory {

    public ICommunicateBaseChannel open(final int port,final String host);

    public ICommunicateBaseChannel open(final int port);

    public LinkedHashMap<Integer, ChannelGroup> getGroups();

    public ICommunicateBaseChannel of(final int localID);

}
