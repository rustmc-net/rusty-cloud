package net.rustmc.cloud.base.communicate;

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

}
