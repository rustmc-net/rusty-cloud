package net.rustmc.cloud.node.handlers;

import net.rustmc.cloud.base.communicate.CommunicateChannelHandler;
import net.rustmc.cloud.base.communicate.ICommunicateChannel;
import net.rustmc.cloud.base.packets.output.groups.PacketOutGroupInfoRequest;
import net.rustmc.cloud.node.RustCloud;
import net.rustmc.cloud.node.common.groups.SimpleRemoteOnlineGroup;
import net.rustmc.cloud.node.groups.IRemoteOnlineGroup;

import java.util.HashMap;
import java.util.Map;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 28.10.2022
 */
public class PacketOutGroupInfoRequestHandler {

    private static final Map<String, IRemoteOnlineGroup> flow = new HashMap<>();

    public PacketOutGroupInfoRequestHandler() {
        RustCloud.getCloud().getCommunicateBaseChannel().getBaseHandlerPool().subscribe(PacketOutGroupInfoRequest.class, new CommunicateChannelHandler<PacketOutGroupInfoRequest>() {
            @Override
            public void handle(PacketOutGroupInfoRequest packet, ICommunicateChannel channel) {
                flow.put(packet.getName(), new SimpleRemoteOnlineGroup(
                        packet.getName(),
                        packet.getVersion(),
                        packet.getMaxPlayers(),
                        packet.getMemory(),
                        packet.getMaxServers(),
                        packet.getMinServer()
                        )
                );
            }
        });
    }

    public static IRemoteOnlineGroup request(final String name) {
        final var result = flow.get(name);
        flow.remove(name);
        return result;
    }

    public static boolean contains(final String name) {
        return flow.containsKey(name);
    }

}
