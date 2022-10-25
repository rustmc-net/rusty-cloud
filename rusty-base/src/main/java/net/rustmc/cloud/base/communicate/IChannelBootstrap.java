package net.rustmc.cloud.base.communicate;

/**
 * this file belongs to the rusty-cloud project.
 *
 * @author Alexander Jilge
 * @since 24.10.2022, Mo.
 */
public interface IChannelBootstrap {

    public IChannelBootstrap port(final int port);

    public IChannelBootstrap host(final String host);

    public ICommunicateBaseChannel open() throws ConnectFailException;

}
