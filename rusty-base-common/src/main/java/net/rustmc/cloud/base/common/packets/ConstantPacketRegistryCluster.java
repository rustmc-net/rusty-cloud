package net.rustmc.cloud.base.common.packets;

import net.rustmc.cloud.base.common.Rust;
import net.rustmc.cloud.base.packets.input.handshake.PacketInDisconnect;
import net.rustmc.cloud.base.packets.input.handshake.PacketInHandshake;
import net.rustmc.cloud.base.packets.output.handshake.PacketOutHandshake;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 25.10.2022
 */
public class ConstantPacketRegistryCluster {

    @SuppressWarnings("unchecked")
    public ConstantPacketRegistryCluster() {
        Rust.getInstance()
                .getCommunicatePacketPool()
                .register(
                        PacketInHandshake.class,
                        PacketOutHandshake.class,
                        PacketInDisconnect.class
                );
    }

}
