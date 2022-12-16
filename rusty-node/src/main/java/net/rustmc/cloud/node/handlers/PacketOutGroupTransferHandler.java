package net.rustmc.cloud.node.handlers;

import lombok.SneakyThrows;
import net.rustmc.cloud.base.common.communicate.files.SimpleChunkedFileHandler;
import net.rustmc.cloud.base.communicate.CommunicateChannelHandler;
import net.rustmc.cloud.base.communicate.ICommunicateChannel;
import net.rustmc.cloud.base.packets.output.transfer.PacketOutGroupTransfer;
import net.rustmc.cloud.base.util.FileHelper;
import net.rustmc.cloud.base.util.ZipHelper;
import net.rustmc.cloud.node.RustCloud;

import java.io.File;
import java.nio.file.Files;

public class PacketOutGroupTransferHandler {

    public PacketOutGroupTransferHandler() {
        RustCloud.getCloud().getCommunicateBaseChannel().getBaseHandlerPool().subscribe(PacketOutGroupTransfer.class, new CommunicateChannelHandler<PacketOutGroupTransfer>() {
            @SuppressWarnings("ResultOfMethodCallIgnored")
            @SneakyThrows
            @Override
            public void handle(PacketOutGroupTransfer packet, ICommunicateChannel channel) {
                final var zip = new File("temp//" + packet.getPrimitiveGroup().name + ".zip");
                zip.createNewFile();
                Files.write(zip.toPath(), SimpleChunkedFileHandler.read());
                final var path = (packet.getPrimitiveGroup().template ? "templates" : "statics") + "//" + packet.getPrimitiveGroup().name;
                FileHelper.create(new File(path));
                ZipHelper.unzip(zip.getPath(), path);
            }
        });
    }

}
