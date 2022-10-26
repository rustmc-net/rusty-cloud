package net.rustmc.cloud.base.common.communicate;

import net.rustmc.cloud.base.communicate.CommunicatePacket;
import net.rustmc.cloud.base.communicate.ICommunicatePacketPool;
import net.rustmc.cloud.base.communicate.PacketIdentifier;

import java.util.HashMap;
import java.util.Map;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
@SuppressWarnings({"unchecked"})
public class SimpleCommunicatePacketPool implements ICommunicatePacketPool {

    private final Map<Character, Class<? extends CommunicatePacket<?>>> pool = new HashMap<>();

    @Override
    public void register(Class<? extends CommunicatePacket<?>>... packets) {
        for (Class<? extends CommunicatePacket<?>> packet : packets) {
            this._register(packet);
        }
    }

    protected void _register(Class<? extends CommunicatePacket<?>> packet) {
        final PacketIdentifier identifier = packet.getAnnotation(PacketIdentifier.class);
        this.pool.put(identifier.identifier(), packet);
    }

    @Override
    public Class<? extends CommunicatePacket<?>> of(char identifier) {
        return this.pool.get(identifier);
    }

    @SuppressWarnings("rawtypes")
    @Override
    public char of(Class<? extends CommunicatePacket> tClass) {
        for (Map.Entry<Character, Class<? extends CommunicatePacket<?>>> entry : this.pool.entrySet()) {
            if (entry.getValue().equals(tClass)) return entry.getKey();
        }
        return '0';
    }
}
