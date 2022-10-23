package net.rustmc.cloud.base.communicate;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
public interface CommunicateChannelHandler<T extends CommunicatePacket<?>> {

    public void handle(T packet, ICommunicateChannel channel);

}
