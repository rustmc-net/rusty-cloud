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
public final class DefaultCloudConsoleImpl implements ICloudConsole {

    private final String prompt = "» ";
    private final LinkedList<Consumer<String>> handlers = new LinkedList<>();
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private final IThread thread = Rust.getInstance().getThreadPool().produce((thread) -> {

        String readLine;
        while (thread.isRunning()) {
            readLine = Rust.getInstance().getConsoleFactory().getCloudLineReader().readLine(prompt);
            for (Consumer<String> handler : handlers) handler.accept(readLine);
            Rust.getInstance().getEventPerformer().perform(new CloudNativeConsoleInputEvent(readLine));
        }

    });

    @Override
    public ICloudConsole send(String output, Output level) {
        switch (level) {
            case INFO ->
                    output = "[" + dateTimeFormatter.format(LocalDateTime.now()) + "] (" + CloudConsoleColor.GREEN.getAnsiCode() + "INFO" + CloudConsoleColor.RESET.getAnsiCode() + ") - " + output + CloudConsoleColor.RESET.getAnsiCode();
            case ERROR ->
                    output = "[" + dateTimeFormatter.format(LocalDateTime.now()) + "] (" + CloudConsoleColor.RED.getAnsiCode() + "ERROR" + CloudConsoleColor.RESET.getAnsiCode() + ") - " + CloudConsoleColor.RED.getAnsiCode() + output + CloudConsoleColor.RESET.getAnsiCode();
            case WARN ->
                    output = "[" + dateTimeFormatter.format(LocalDateTime.now()) + "] (" + CloudConsoleColor.YELLOW.getAnsiCode() + "WARN" + CloudConsoleColor.RESET.getAnsiCode() + ") - " + CloudConsoleColor.YELLOW.getAnsiCode() + output + CloudConsoleColor.RESET.getAnsiCode();
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

    @Override
    public ICloudConsole run() {
        this.thread.run();
        return this;
    }

    @Override
    public void close() {
        Rust.getInstance().getConsoleFactory().getCloudLineReader().getTerminal().reader().shutdown();
        Rust.getInstance().getConsoleFactory().getCloudLineReader().getTerminal().pause();
        this.thread.close();
    }

    @Override
    public ICloudConsole push(Consumer<String> handler) {
        this.handlers.push(handler);
        return this;
    }
}
