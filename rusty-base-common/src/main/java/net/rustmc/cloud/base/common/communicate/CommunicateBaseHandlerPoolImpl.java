package net.rustmc.cloud.base.common.communicate;

import io.netty.channel.ChannelHandlerContext;
import net.rustmc.cloud.base.communicate.CommunicateChannelHandler;
import net.rustmc.cloud.base.communicate.CommunicatePacket;
import net.rustmc.cloud.base.communicate.ICommunicateBaseHandlerPool;

import java.util.*;
import java.util.function.Consumer;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
@SuppressWarnings("rawtypes")
public final class CommunicateBaseHandlerPoolImpl implements ICommunicateBaseHandlerPool {

    private final HashMap<Class<?>, List<CommunicateChannelHandler>> handlers = new HashMap<>();
    private final List<Consumer<ChannelHandlerContext>> bootHandlers = new ArrayList<>();
    private final List<Consumer<ChannelHandlerContext>> closeHandlers = new ArrayList<>();

    @Override
    public <T extends CommunicatePacket<?>> void subscribe(Class<T> tClass, CommunicateChannelHandler<T> handler) {
        if (this.handlers.containsKey(tClass))
            this.handlers.get(tClass).add(handler);
                else this.handlers.put(tClass, new ArrayList<>(Collections.singleton(handler)));
    }

    @Override
    public void subscribeBootHandler(Consumer<ChannelHandlerContext> handler) {
        this.bootHandlers.add(handler);
    }

    @Override
    public void subscribeCloseHandler(Consumer<ChannelHandlerContext> handler) {
        this.closeHandlers.add(handler);
    }

    @Override
    public Collection<CommunicateChannelHandler> handlers(Class<?> tClass) {
        if (!this.handlers.containsKey(tClass))
            return Collections.emptyList();
        return this.handlers.get(tClass);
    }

    @Override
    public List<Consumer<ChannelHandlerContext>> getBootHandlers() {
        return bootHandlers;
    }

    @Override
    public List<Consumer<ChannelHandlerContext>> getCloseHandlers() {
        return closeHandlers;
    }

}
