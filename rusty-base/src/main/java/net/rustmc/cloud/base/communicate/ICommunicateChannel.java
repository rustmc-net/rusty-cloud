package net.rustmc.cloud.base.communicate;

import java.net.SocketAddress;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
public interface ICommunicateChannel {

    public void decline();

    public SocketAddress getAddress();

    public String getUniqueID();

    public String getShortID();

}
