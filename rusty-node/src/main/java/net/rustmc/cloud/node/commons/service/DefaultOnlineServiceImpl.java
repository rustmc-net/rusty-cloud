package net.rustmc.cloud.node.commons.service;

import lombok.Getter;
import net.rustmc.cloud.base.common.Rust;
import net.rustmc.cloud.node.commons.groups.Group;
import net.rustmc.cloud.node.groups.IOnlineGroup;
import net.rustmc.cloud.node.service.INativeCachedConsole;
import net.rustmc.cloud.node.service.INativeOnlineService;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 16.12.2022
 */
@Getter
public class DefaultOnlineServiceImpl implements INativeOnlineService {

    @Getter
    public static HashMap<String, INativeOnlineService> onlineServices = new HashMap<>();

    private final Process process;
    private final IOnlineGroup group;
    private final int count;
    private final INativeCachedConsole console = new DefaultNativeConsoleImpl();

    public DefaultOnlineServiceImpl(Process process, Group group, int count) {
        this.process = process;
        this.group = group;
        this.count = count;
        Rust.getInstance().getAsynchronousExecutor().submit(() -> {
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while (true) {
                try {
                    if ((line = reader.readLine()) == null) break;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                console.register(line);
            }
        });
    }

    @Override
    public void shutdown() {
        try {
            this.process.getOutputStream().write("stop".getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            this.process.destroy();
        }
    }

    @Override
    public String getName() {
        return this.group.getObject().getName() + "-" + this.count;
    }

    @Override
    public void command(String command) {
        try {
            this.process.getOutputStream().write(command.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public OutputStream getOutputStream() {
        return this.process.getOutputStream();
    }

    @Override
    public InputStream getInputstream() {
        return this.process.getInputStream();
    }

    @Override
    public InputStream getErrorStream() {
        return this.process.getErrorStream();
    }

    @Override
    public INativeCachedConsole getServiceConsole() {
        return this.console;
    }
}
