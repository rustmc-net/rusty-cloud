package net.rustmc.cloud.base.packets;

import io.netty.buffer.ByteBuf;
import net.rustmc.cloud.base.communicate.CommunicatePacket;
import net.rustmc.cloud.base.communicate.PacketIdentifier;
import net.rustmc.cloud.base.communicate.files.ICommunicateFile;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 26.10.2022
 */
@PacketIdentifier(identifier = 'h')
public class PacketInOutFile extends CommunicatePacket<PacketInOutFile> {

    private ICommunicateFile communicateFile;

    public PacketInOutFile(ICommunicateFile communicateFile) {
        this.communicateFile = communicateFile;
    }

    public PacketInOutFile() {
    }

    @Override
    public void decode(ByteBuf buf) {
        communicateFile.decode(buf);
    }

    @Override
    public void encode(ByteBuf buf) {
        communicateFile.encode(buf);
    }

}
