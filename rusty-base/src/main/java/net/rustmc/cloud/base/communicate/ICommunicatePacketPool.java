package net.rustmc.cloud.base.communicate;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
@SuppressWarnings({"unused", "unchecked", "rawtypes"})
public interface ICommunicatePacketPool {

    public void register(final Class<? extends CommunicatePacket<?>>... packets);

    public Class<? extends CommunicatePacket<?>> of(final char identifier);

    public char of(final Class<? extends CommunicatePacket> tClass);

}
