package net.rustmc.cloud.node.handlers;

import net.rustmc.cloud.base.communicate.CommunicateChannelHandler;
import net.rustmc.cloud.base.communicate.ICommunicateChannel;
import net.rustmc.cloud.base.packets.output.groups.PacketOutGroupEmploy;
import net.rustmc.cloud.base.util.ZipHelper;
import net.rustmc.cloud.node.RustCloud;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 01.11.2022
 */
public class PacketOutGroupEmployHandler {

    public PacketOutGroupEmployHandler() {
        RustCloud.getCloud().getCommunicateBaseChannel().getBaseHandlerPool().subscribe(PacketOutGroupEmploy.class, new CommunicateChannelHandler<PacketOutGroupEmploy>() {
            @Override
            public void handle(PacketOutGroupEmploy packet, ICommunicateChannel channel) {
                RustCloud.getCloud().getCloudConsole().send("new input");
                final var out = new File("temp//" + packet.getName() + ".zip");
                try {
                    Files.write(out.toPath(), packet.getDirectoryContent());
                    ZipHelper.unzip(out.getPath(), "storages//" + packet.getName());

                    RustCloud.getCloud().getGroupFactory().logIn(RustCloud.getCloud().getGroupFactory().of(
                            packet.getName(),
                            packet.getMemory(),
                            packet.getMaxServers(),
                            packet.getMinServers(),
                            packet.getVersion(),
                            packet.getMaxPlayers())
                    );

                    RustCloud.getCloud().getCloudConsole().send("The §a" + packet.getName() + " §rgroup has been logged in.");

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}
