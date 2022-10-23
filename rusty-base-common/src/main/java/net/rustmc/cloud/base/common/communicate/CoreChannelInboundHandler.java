package net.rustmc.cloud.base.common.communicate;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.val;
import net.rustmc.cloud.base.common.Rust;
import net.rustmc.cloud.base.communicate.CommunicateChannelHandler;
import net.rustmc.cloud.base.communicate.CommunicatePacket;
import net.rustmc.cloud.base.communicate.ICommunicateBaseHandlerPool;

import java.util.function.Consumer;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
public class CoreChannelInboundHandler extends SimpleChannelInboundHandler<CommunicatePacket<?>> {

    private final ICommunicateBaseHandlerPool handlerPool;
    private final int localID;

    public CoreChannelInboundHandler(ICommunicateBaseHandlerPool handlerPool, int localID) {
        this.handlerPool = handlerPool;
        this.localID = localID;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, CommunicatePacket<?> packet) {
        Class<? extends CommunicatePacket> type = packet.getClass();
        val cape = DefaultChannelImpl.newChannel(channelHandlerContext.channel());
        for (CommunicateChannelHandler handler : this.handlerPool.handlers(type)) {
            handler.handle(packet, cape);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (Consumer<ChannelHandlerContext> handler : this.handlerPool.getBootHandlers()) {
            handler.accept(ctx);
        }
        if (Rust.getInstance().getChannelFactory().of(this.localID).isClient()) {
            Rust.getInstance().getChannelFactory().getGroups().get(localID).add(ctx.channel());
        }
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        for (Consumer<ChannelHandlerContext> handler : this.handlerPool.getCloseHandlers()) {
            handler.accept(ctx);
        }
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }

}
