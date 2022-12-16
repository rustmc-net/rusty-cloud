package net.rustmc.cloud.node.commons.groups;

import net.rustmc.cloud.base.common.Rust;
import net.rustmc.cloud.base.objects.SimpleCloudGroup;
import net.rustmc.cloud.base.util.FileHelper;
import net.rustmc.cloud.node.RustCloud;
import net.rustmc.cloud.node.groups.IOnlineGroup;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.LinkedList;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 21.11.2022
 */
public abstract class Group implements IOnlineGroup {

    protected final SimpleCloudGroup cloudGroup;
    protected LinkedList<Process> tasks = new LinkedList<>();

    public Group(SimpleCloudGroup cloudGroup) {
        this.cloudGroup = cloudGroup;
    }

    @Override
    public void start() {
        int count = this.cloudGroup.getMinServers();
        for (int i = 1; i<count; i++) {
            final var file = new File("temp//" + this.cloudGroup.getName() + "//" + this.cloudGroup.getName() + "-" + count);
            FileHelper.create(file);
            final var files = file.listFiles();
            if (files != null) {
                for (File copy : files) {
                    if (!copy.isDirectory()) {
                        Rust.getInstance().getAsynchronousExecutor().submit(() -> {
                            try {
                                FileHelper.copyFile(copy.getPath(), file.getPath() + "//" + copy.getName());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                    } else {
                        Rust.getInstance().getAsynchronousExecutor().submit(() -> FileHelper.copyDir(copy, file.getPath()));
                    }
                }
            }
            ProcessBuilder processBuilder;
            if (this.cloudGroup.isProxy()) {
                processBuilder = new ProcessBuilder(("java -jar -Xms" + this.cloudGroup.getName() + "M -Xmx" + this.cloudGroup.getMemory() + "M proxy.jar").split(" "));
                RustCloud.getCloud().getCloudConsole().send("starting §b" + this.cloudGroup.getName() + " §ron default minecraft port.");
            } else {
                int port = searchPort();
                processBuilder = new ProcessBuilder(("java -jar -Xms" + this.cloudGroup.getMemory() + "M -Xmx" + this.cloudGroup.getMemory() + "M -Dcom.mojang.eula.agree=true spigot.jar nogui --online-mode false --max-players " + this.cloudGroup.getMaxPlayersPer() + " --noconsole --port " + port).split(" "));
                RustCloud.getCloud().getCloudConsole().send("Starting §b" + this.cloudGroup.getName() + " §ron port " + port + ".");
            }
            processBuilder.directory(file);
            Process process = null;
            try {
                process = processBuilder.start();
                process.waitFor();
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            this.tasks.addLast(process);
        }
    }

    @Override
    public abstract void shutdown();

    public abstract String getPath();

    @Override
    public SimpleCloudGroup getObject() {
        return this.cloudGroup;
    }

    protected int searchPort() {
        int port = 0;
        try {
            ServerSocket socket = new ServerSocket(0);
            port = socket.getLocalPort();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return port;
    }

}
