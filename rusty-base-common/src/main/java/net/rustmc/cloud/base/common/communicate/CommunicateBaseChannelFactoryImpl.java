package net.rustmc.cloud.base.common.communicate;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.SneakyThrows;
import net.rustmc.cloud.base.communicate.ICommunicateBaseChannel;
import net.rustmc.cloud.base.communicate.ICommunicateBaseChannelFactory;
import net.rustmc.cloud.base.communicate.ICommunicateBaseHandlerPool;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
public class CommunicateBaseChannelFactoryImpl implements ICommunicateBaseChannelFactory {

    private final LinkedHashMap<Integer, ChannelGroup> _groups = new LinkedHashMap<Integer, ChannelGroup>();

    private final HashMap<Integer, ICommunicateBaseChannel> channels = new HashMap<>();

    @SneakyThrows
    @Override
    public ICommunicateBaseChannel open(int port, String host) {
        final int localID = this._groups.size()+1;
        final EventLoopGroup eventLoopGroup = Epoll.isAvailable() ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        final ICommunicateBaseHandlerPool handlerPool = new CommunicateBaseHandlerPoolImpl();
        final Channel channel = new Bootstrap()
                .group(eventLoopGroup)
                .handler(new CoreBaseChannelInitializer(handlerPool, localID))
                .channel(Epoll.isAvailable() ? EpollSocketChannel.class : NioSocketChannel.class)
                .connect(host, port).sync().channel();
        final DefaultCommunicateBaseChannelImpl defaultCommunicateBaseChannel = new DefaultCommunicateBaseChannelImpl(channel, handlerPool, true, localID);
        this.channels.put(localID, defaultCommunicateBaseChannel);
        return defaultCommunicateBaseChannel;
    }

    @SneakyThrows
    @Override
    public ICommunicateBaseChannel open(int port) {
        final int localID = this._groups.size()+1;
        final EventLoopGroup eventLoopGroup = Epoll.isAvailable() ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        final ICommunicateBaseHandlerPool handlerPool = new CommunicateBaseHandlerPoolImpl();
        final Channel channel = new ServerBootstrap()
                .group(eventLoopGroup)
                .childHandler(new CoreBaseChannelInitializer(handlerPool, localID))
                .channel(Epoll.isAvailable() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                .bind(port).sync().channel();
        final DefaultCommunicateBaseChannelImpl defaultCommunicateBaseChannel = new DefaultCommunicateBaseChannelImpl(channel, handlerPool, false, localID);
        this.channels.put(localID, defaultCommunicateBaseChannel);
        return defaultCommunicateBaseChannel;
    }

    @Override
    public LinkedHashMap<Integer, ChannelGroup> getGroups() {
        return this._groups;
    }

    @Override
    public ICommunicateBaseChannel of(int localID) {
        return this.channels.get(localID);
    }

}
