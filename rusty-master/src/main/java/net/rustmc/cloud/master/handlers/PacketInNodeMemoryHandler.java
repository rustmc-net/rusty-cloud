package net.rustmc.cloud.master.handlers;

import net.rustmc.cloud.base.communicate.CommunicateChannelHandler;
import net.rustmc.cloud.base.communicate.ICommunicateChannel;
import net.rustmc.cloud.base.console.ICloudConsole;
import net.rustmc.cloud.base.packets.input.nodes.PacketInNodeMemory;
import net.rustmc.cloud.base.packets.output.groups.PacketOutGroupEmploy;
import net.rustmc.cloud.base.util.ZipHelper;
import net.rustmc.cloud.master.RustCloud;
import net.rustmc.cloud.master.groups.IOfflineGroup;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.function.Predicate;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 28.10.2022
 */
public class PacketInNodeMemoryHandler {

    public PacketInNodeMemoryHandler() {
        RustCloud.getCloud().getCommunicateBaseChannel().getBaseHandlerPool().subscribe(PacketInNodeMemory.class, new CommunicateChannelHandler<PacketInNodeMemory>() {
            @SuppressWarnings("DataFlowIssue")
            @Override
            public void handle(PacketInNodeMemory packet, ICommunicateChannel channel) {
                final var node = RustCloud.getCloud()
                        .getNodeManager()
                        .getNodeByNodeKey(packet.getNodeKey());
                RustCloud.getCloud()
                        .getOpenedNodePool()
                        .getByNodeKey(
                                node.getNodeKey())
                        .setFreeMemory(packet.getMemory());
                if (RustCloud.getCloud().getConfiguration().getMinRam() <= packet.getMemory()) {
                    int current = RustCloud.getCloud()
                            .getOpenedNodePool()
                            .getByNodeKey(node.getNodeKey())
                            .getFreeMemory();
                    RustCloud.getCloud().getCloudConsole().send("The node " + node.getName() + " §rcan still be allocated §b" + current + " §rMB of memory.");

                    RustCloud.getCloud().getGroupRequestQueue().consume(new Predicate<IOfflineGroup>() {
                        @Override
                        public boolean test(IOfflineGroup offlineGroup) {
                            if (offlineGroup.getObject().getMemory() <= current) {
                                final var file = new File("temp//" + offlineGroup.getGroupName() + ".zip");
                                if (!file.exists()) {
                                    ZipHelper.zipFoldersAndFiles(
                                            new File("groups//" + offlineGroup.getGroupName()).toPath(),
                                            new File("temp//" + offlineGroup.getGroupName() + ".zip").toPath()
                                    );
                                }
                                try {
                                    RustCloud.getCloud()
                                            .getCommunicateBaseChannel()
                                            .dispatch(
                                                    new PacketOutGroupEmploy(
                                                            toObjects(Files.readAllBytes(file.toPath())),
                                                            offlineGroup.getGroupName(),
                                                            offlineGroup.getObject().getMemory())
                                            , channel.getUniqueID());
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                                RustCloud.getCloud().getCloudConsole().send("A new group §a" + offlineGroup.getGroupName() + " §rhas been employed to §e" + channel.getAddress().toString() + "§r.");
                                return true;
                            }
                            return false;
                        }
                    });

                } else {
                    RustCloud
                            .getCloud()
                            .getCloudConsole()
                            .send("The allocation of memory fills the capacity of the node: §e" + node.getName(), ICloudConsole.Output.WARN);
                }
            }
        });
    }

    Byte[] toObjects(byte[] bytesPrim) {
        Byte[] bytes = new Byte[bytesPrim.length];
        int i = 0;
        for (byte b : bytesPrim) bytes[i++] = b;
        return bytes;
    }

}
