package net.rustmc.cloud.base.communicate;

import io.netty.channel.ChannelHandlerContext;

import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
public interface ICommunicateBaseHandlerPool {

    public <T extends CommunicatePacket<?>> void subscribe(Class<T> tClass, CommunicateChannelHandler<T> handler);

    public void subscribeBootHandler(Consumer<ChannelHandlerContext> handler);

    public void subscribeCloseHandler(Consumer<ChannelHandlerContext> handler);

    @SuppressWarnings("rawtypes")
    public Collection<CommunicateChannelHandler> handlers(Class<?> tClass);

    public List<Consumer<ChannelHandlerContext>> getBootHandlers();

    public List<Consumer<ChannelHandlerContext>> getCloseHandlers();


}
