package net.rustmc.cloud.node.handlers;

import net.rustmc.cloud.base.common.communicate.files.SimpleChunkedFileHandler;
import net.rustmc.cloud.base.communicate.CommunicateChannelHandler;
import net.rustmc.cloud.base.communicate.ICommunicateChannel;
import net.rustmc.cloud.base.packets.output.transfer.PacketOutGroupTransfer;
import net.rustmc.cloud.node.RustCloud;

import java.io.File;

public class PacketOutGroupTransferHandler {

    public PacketOutGroupTransferHandler() {
        RustCloud.getCloud().getCommunicateBaseChannel().getBaseHandlerPool().subscribe(PacketOutGroupTransfer.class, new CommunicateChannelHandler<PacketOutGroupTransfer>() {
            @Override
            public void handle(PacketOutGroupTransfer packet, ICommunicateChannel channel) {
                final var content = SimpleChunkedFileHandler.read();
                /* todo: handle the income group content */
            }
        });
    }

}
