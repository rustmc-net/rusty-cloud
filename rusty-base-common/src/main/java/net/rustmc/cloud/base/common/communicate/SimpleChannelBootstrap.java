package net.rustmc.cloud.base.common.communicate;

import net.rustmc.cloud.base.common.Rust;
import net.rustmc.cloud.base.communicate.IChannelBootstrap;
import net.rustmc.cloud.base.communicate.ICommunicateBaseChannel;

/**
 * this file belongs to the rusty-cloud project.
 *
 * @author Alexander Jilge
 * @since 24.10.2022, Mo.
 */
public class SimpleChannelBootstrap implements IChannelBootstrap {

    private int port;
    private String host;

    @Override
    public IChannelBootstrap port(int port) {
        this.port = port;
        return this;
    }

    @Override
    public IChannelBootstrap host(String host) {
        this.host = host;
        return this;
    }

    @Override
    public ICommunicateBaseChannel open() {
        return this.host == null ? Rust.getInstance().getChannelFactory().open(port) : Rust.getInstance().getChannelFactory().open(port, host);
    }
}
