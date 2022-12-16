package net.rustmc.cloud.base.common.packets;

import net.rustmc.cloud.base.common.Rust;
import net.rustmc.cloud.base.packets.PacketPauseCodec;
import net.rustmc.cloud.base.packets.input.handshake.PacketInDisconnect;
import net.rustmc.cloud.base.packets.input.handshake.PacketInHandshake;
import net.rustmc.cloud.base.packets.output.PacketOutGroupInfo;
import net.rustmc.cloud.base.packets.output.PacketOutGroupStart;
import net.rustmc.cloud.base.packets.output.PacketOutGroupStop;
import net.rustmc.cloud.base.packets.output.handshake.PacketOutHandshake;
import net.rustmc.cloud.base.packets.output.service.PacketOutServiceCommand;
import net.rustmc.cloud.base.packets.output.service.PacketOutServiceShutdown;
import net.rustmc.cloud.base.packets.output.transfer.PacketOutGroupTransfer;

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
                        PacketInDisconnect.class,
                        PacketOutGroupInfo.class,
                        PacketOutGroupStop.class,
                        PacketOutGroupStart.class,
                        PacketOutGroupTransfer.class,
                        PacketOutServiceShutdown.class,
                        PacketOutServiceCommand.class,
                        PacketPauseCodec.class
                );
    }

}
