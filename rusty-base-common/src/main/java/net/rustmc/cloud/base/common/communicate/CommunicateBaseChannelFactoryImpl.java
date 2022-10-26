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
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;
import lombok.SneakyThrows;
import net.rustmc.cloud.base.communicate.IChannelBootstrap;
import net.rustmc.cloud.base.communicate.ICommunicateBaseChannel;
import net.rustmc.cloud.base.communicate.ICommunicateBaseChannelFactory;
import net.rustmc.cloud.base.communicate.ICommunicateBaseHandlerPool;
import net.rustmc.cloud.base.util.Pair;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
public class CommunicateBaseChannelFactoryImpl implements ICommunicateBaseChannelFactory {

    private final LinkedHashMap<Integer, ChannelGroup> _groups = new LinkedHashMap<Integer, ChannelGroup>();

    private final HashMap<Integer, Pair<EventLoopGroup, ICommunicateBaseChannel>> channels = new HashMap<>();

    @SneakyThrows
    @Override
    public ICommunicateBaseChannel open(int port, String host) {
        final var client = true;
        final int localID = this._groups.size()+1;
        final EventLoopGroup eventLoopGroup = Epoll.isAvailable() ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        final ICommunicateBaseHandlerPool handlerPool = new CommunicateBaseHandlerPoolImpl();
        final Channel channel = new Bootstrap()
                .group(eventLoopGroup)
                .handler(new CoreBaseChannelInitializer(handlerPool, localID, client))
                .channel(Epoll.isAvailable() ? EpollSocketChannel.class : NioSocketChannel.class)
                .connect(host, port).sync().channel();
        final DefaultCommunicateBaseChannelImpl defaultCommunicateBaseChannel = new DefaultCommunicateBaseChannelImpl(channel, handlerPool, client, localID);
        this.channels.put(localID, new Pair<>(eventLoopGroup, defaultCommunicateBaseChannel));
        return defaultCommunicateBaseChannel;
    }

    @SneakyThrows
    @Override
    public ICommunicateBaseChannel open(int port) {
        final int localID = this._groups.size()+1;
        this._groups.put(localID, new DefaultChannelGroup(GlobalEventExecutor.INSTANCE));
        final EventLoopGroup eventLoopGroup = Epoll.isAvailable() ? new EpollEventLoopGroup() : new NioEventLoopGroup();
        final ICommunicateBaseHandlerPool handlerPool = new CommunicateBaseHandlerPoolImpl();
        final Channel channel = new ServerBootstrap()
                .group(eventLoopGroup)
                .childHandler(new CoreBaseChannelInitializer(handlerPool, localID, false))
                .channel(Epoll.isAvailable() ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
                .bind(port).sync().channel();
        final DefaultCommunicateBaseChannelImpl defaultCommunicateBaseChannel = new DefaultCommunicateBaseChannelImpl(channel, handlerPool, false, localID);
        this.channels.put(localID, new Pair<>(eventLoopGroup, defaultCommunicateBaseChannel));
        return defaultCommunicateBaseChannel;
    }

    @Override
    public LinkedHashMap<Integer, ChannelGroup> getGroups() {
        return this._groups;
    }

    @Override
    public ICommunicateBaseChannel of(int localID) {
        return this.channels.get(localID).getSecond();
    }

    @Override
    public EventLoopGroup getEventLoopGroup(int localID) {
        return this.channels.get(localID).getFirst();
    }

    @Override
    public IChannelBootstrap bootstrap() {
        return new SimpleChannelBootstrap();
    }

    @Override
    public void close() {
        for (final var entry : this.channels.entrySet()) {
            entry.getValue().getFirst().shutdownGracefully();
            entry.getValue().getSecond().decline();
        }
    }

}
