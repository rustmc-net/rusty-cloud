package net.rustmc.cloud.base.common.packets;

import net.rustmc.cloud.base.common.Rust;
import net.rustmc.cloud.base.packets.input.PacketInOutFile;
import net.rustmc.cloud.base.packets.input.groups.PacketInGroupInfoRequest;
import net.rustmc.cloud.base.packets.input.nodes.PacketInNodeCacheGroups;
import net.rustmc.cloud.base.packets.input.nodes.PacketInNodeMemory;
import net.rustmc.cloud.base.packets.output.groups.PacketOutGroupInfoRequest;
import net.rustmc.cloud.base.packets.output.nodes.PacketOutNodeMemory;
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
                        PacketInOutFile.class,
                        PacketOutNodeMemory.class,
                        PacketInGroupInfoRequest.class,
                        PacketOutGroupInfoRequest.class,
                        PacketInOutFile.class,
                        PacketInNodeMemory.class,
                        PacketInNodeCacheGroups.class
                );
    }

}
