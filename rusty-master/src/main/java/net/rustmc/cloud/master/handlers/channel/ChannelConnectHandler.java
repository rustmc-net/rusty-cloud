package net.rustmc.cloud.master.handlers.channel;

import io.netty.channel.ChannelHandlerContext;
import net.rustmc.cloud.base.console.ICloudConsole;
import net.rustmc.cloud.master.RustCloud;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 25.10.2022
 */
public class ChannelConnectHandler {

    public ChannelConnectHandler() {
        RustCloud.getCloud().getCommunicateBaseChannel().getBaseHandlerPool().subscribeBootHandler(new Consumer<ChannelHandlerContext>() {
            @Override
            public void accept(ChannelHandlerContext channelHandlerContext) {
                RustCloud.getCloud().getChannelFlow().queue(channelHandlerContext.channel());
                RustCloud.getCloud().getScheduledExecutorService().schedule(new Runnable() {
                    @Override
                    public void run() {
                        if (RustCloud.getCloud().getChannelFlow().contains(channelHandlerContext.channel().id().asShortText())) {
                            channelHandlerContext.channel().eventLoop().shutdownGracefully();
                            channelHandlerContext.channel().pipeline().close();
                            RustCloud.getCloud().getCloudConsole().send("A malicious client has been identified! -> §c" + channelHandlerContext
                                    .channel()
                                    .remoteAddress()
                                    .toString(),
                                    ICloudConsole.Output.WARN);
                        }
                    }
                }, RustCloud.getCloud()
                        .getConfiguration()
                        .getClientTimeOutDiscard(),
                        TimeUnit.MILLISECONDS
                );
            }
        });
    }

}
