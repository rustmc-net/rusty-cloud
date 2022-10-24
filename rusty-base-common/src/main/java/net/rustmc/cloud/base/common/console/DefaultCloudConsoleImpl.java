package net.rustmc.cloud.base.common.console;

import net.rustmc.cloud.base.common.Rust;
import net.rustmc.cloud.base.common.events.natives.CloudNativeConsoleInputEvent;
import net.rustmc.cloud.base.console.CloudConsoleColor;
import net.rustmc.cloud.base.console.ICloudConsole;
import net.rustmc.cloud.base.threads.IThread;
import org.jline.reader.LineReader;
import org.jline.utils.InfoCmp;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.function.Consumer;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
@SuppressWarnings("FieldCanBeLocal")
public class DefaultCloudConsoleImpl implements ICloudConsole {

    private final String prompt = "» ";
    private final LinkedList<Consumer<String>> handlers = new LinkedList<>();
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private final Thread thread = new Thread(() -> {
        String readLine;
        while (!DefaultCloudConsoleImpl.this.thread.isInterrupted()) {
            readLine = Rust.getInstance().getConsoleFactory().getCloudLineReader().readLine(prompt);
            for (Consumer<String> handler : handlers) handler.accept(readLine);
            Rust.getInstance().getEventPerformer().perform(new CloudNativeConsoleInputEvent(readLine));
        }
    });

    public DefaultCloudConsoleImpl() {
        this.clear();
    }

    @Override
    public ICloudConsole send(String output, Output level) {
        output = this._color(output);
        switch (level) {
            case INFO ->
                    output = dateTimeFormatter.format(LocalDateTime.now()) + " | " + CloudConsoleColor.GREEN.getAnsiCode() + "INFO" + CloudConsoleColor.RESET + " » " + output + CloudConsoleColor.RESET;
            case ERROR ->
                    output = dateTimeFormatter.format(LocalDateTime.now()) + " | " + CloudConsoleColor.RED.getAnsiCode() + "ERROR" + CloudConsoleColor.RESET + " » " + CloudConsoleColor.RESET + output + CloudConsoleColor.RESET;
            case WARN ->
                    output = dateTimeFormatter.format(LocalDateTime.now()) + " | " + CloudConsoleColor.YELLOW.getAnsiCode() + "WARN" + CloudConsoleColor.RESET + " » " + CloudConsoleColor.RESET + output + CloudConsoleColor.RESET;
        }
        Rust.getInstance().getConsoleFactory().getCloudLineReader().getTerminal().puts(InfoCmp.Capability.carriage_return);
        Rust.getInstance().getConsoleFactory().getCloudLineReader().getTerminal().writer().println(output);
        Rust.getInstance().getConsoleFactory().getCloudLineReader().getTerminal().flush();
        if (Rust.getInstance().getConsoleFactory().getCloudLineReader().isReading()) {
            Rust.getInstance().getConsoleFactory().getCloudLineReader().callWidget(org.jline.reader.LineReader.REDRAW_LINE);
            Rust.getInstance().getConsoleFactory().getCloudLineReader().callWidget(org.jline.reader.LineReader.REDISPLAY);
        }
        return this;
    }

    @Override
    public ICloudConsole send(String output) {
        return send(output, Output.INFO);
    }

    @Override
    public ICloudConsole send(String service, String output) {
        return this;
    }

    @Override
    public void clear() {
        if (Rust.getInstance().getOperatingSystem().contains("Windows")) {
            try {
                ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "cls");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            } catch (RuntimeException exception) {
                exception.printStackTrace();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                ProcessBuilder pb = new ProcessBuilder("clear");
                Process startProcess = pb.inheritIO().start();
                startProcess.waitFor();
            } catch (RuntimeException exception) {
                exception.printStackTrace();
            } catch (IOException | InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String _color(String code) {
        return code
                .replace("§c", CloudConsoleColor.RED.getAnsiCode())
                .replace("§a", CloudConsoleColor.GREEN.getAnsiCode())
                .replace("§e", CloudConsoleColor.YELLOW.getAnsiCode())
                .replace("§6", CloudConsoleColor.ORANGE.getAnsiCode())
                .replace("§r", CloudConsoleColor.RESET.getAnsiCode())
                .replace("§7", CloudConsoleColor.RESET.getAnsiCode())
                .replace("§b", CloudConsoleColor.CYAN.getAnsiCode()
                );
    }

    @Override
    public void run() {
        this.thread.start();
    }

    @Override
    public void close() {
        Rust.getInstance().getConsoleFactory().getCloudLineReader().getTerminal().reader().shutdown();
        Rust.getInstance().getConsoleFactory().getCloudLineReader().getTerminal().pause();
        this.thread.interrupt();
    }

    @Override
    public ICloudConsole push(Consumer<String> handler) {
        this.handlers.push(handler);
        return this;
    }
}
