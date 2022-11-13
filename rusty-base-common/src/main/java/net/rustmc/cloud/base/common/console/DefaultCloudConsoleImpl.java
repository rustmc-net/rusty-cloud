package net.rustmc.cloud.base.common.console;

import net.rustmc.cloud.base.common.Rust;
import net.rustmc.cloud.base.common.events.natives.CloudNativeConsoleInputEvent;
import net.rustmc.cloud.base.console.CloudConsoleColor;
import net.rustmc.cloud.base.console.ICloudConsole;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.TerminalBuilder;
import org.jline.utils.InfoCmp;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
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

    private final LineReader lineReader = LineReaderBuilder.builder()
            .terminal(TerminalBuilder.builder()
            .system(true)
            .streams(System.in, System.out)
            .encoding(StandardCharsets.UTF_8)
            .dumb(true)
            .build())
            .completer(new DefaultConsoleCompleter())
            .option(LineReader.Option.DISABLE_EVENT_EXPANSION, true)
            .option(LineReader.Option.AUTO_REMOVE_SLASH, false)
            .option(LineReader.Option.INSERT_TAB, false)
            .build();
    private final String prompt = "» ";
    private final LinkedList<Consumer<String>> handlers = new LinkedList<>();
    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm");
    private final Thread thread = new Thread(() -> {
        String line;
        while (!Thread.currentThread().isInterrupted()) {
            line = lineReader.readLine(prompt);
            for (Consumer<String> handler : handlers) handler.accept(line);
            Rust.getInstance().getEventPerformer().perform(new CloudNativeConsoleInputEvent(line));
        }
    });

    public DefaultCloudConsoleImpl() throws IOException {
        this.clear();
        lineReader.setAutosuggestion(LineReader.SuggestionType.COMPLETER);
    }

    @Override
    public ICloudConsole send(String output, Output level) {
        output = this._color(output);
        switch (level) {
            case INFO -> output = " | " +  dateTimeFormatter.format(LocalDateTime.now()) + " | " + CloudConsoleColor.GREEN.getAnsiCode() + "INFO" + CloudConsoleColor.RESET + " - [" + name("base") + "] : " + output + CloudConsoleColor.RESET;
            case ERROR -> output = " | " +  dateTimeFormatter.format(LocalDateTime.now()) + " | " + CloudConsoleColor.RED.getAnsiCode() + "ERRO" + CloudConsoleColor.RESET +" - [" + name("base") + "] : " + CloudConsoleColor.RESET + output + CloudConsoleColor.RESET;
            case WARN -> output = " | " +  dateTimeFormatter.format(LocalDateTime.now()) + " | " + CloudConsoleColor.YELLOW.getAnsiCode() + "WARN" + CloudConsoleColor.RESET + " - [" + name("base") + "] : " + CloudConsoleColor.RESET + output + CloudConsoleColor.RESET;
        }
        lineReader.getTerminal().puts(InfoCmp.Capability.carriage_return);
        lineReader.getTerminal().writer().println(output);
        lineReader.getTerminal().flush();
        if (lineReader.isReading()) {
            lineReader.callWidget(org.jline.reader.LineReader.REDRAW_LINE);
            lineReader.callWidget(org.jline.reader.LineReader.REDISPLAY);
        }
        return this;
    }

    protected String name(String input) {
        if (input.length() >= 11) {
            return " " + input.substring(0, 11) + " ";
        } else {
            StringBuilder builder = new StringBuilder(" ");
            int size = 11;
            for (char c : input.toCharArray()) {
                builder.append(c);
                size--;
            }
            builder.append(" ".repeat(Math.max(0, size)));
            return builder.toString();
        }
    }

    @Override
    public ICloudConsole send(String output) {
        return send(output, Output.INFO);
    }

    @Override
    public ICloudConsole send(Object module, String output, Output level) {
        output = this._color(output);
        switch (level) {
            case INFO -> output = " | " +  dateTimeFormatter.format(LocalDateTime.now()) + " | " + CloudConsoleColor.GREEN.getAnsiCode() + "INFO" + CloudConsoleColor.RESET + " - [" + name(module.getClass().getSimpleName().toLowerCase().replace("pool", "")) + "] : " + output + CloudConsoleColor.RESET;
            case ERROR -> output = " | " +  dateTimeFormatter.format(LocalDateTime.now()) + " | " + CloudConsoleColor.RED.getAnsiCode() + "ERRO" + CloudConsoleColor.RESET + " - [ " + module.getClass().getSimpleName().toLowerCase().replace("pool", "") + " ] : " + CloudConsoleColor.RESET + output + CloudConsoleColor.RESET;
            case WARN -> output = " | " +  dateTimeFormatter.format(LocalDateTime.now()) + " | " + CloudConsoleColor.YELLOW.getAnsiCode() + "WARN" + CloudConsoleColor.RESET + " - [ " + module.getClass().getSimpleName().toLowerCase().replace("pool", "") + " ] : " + CloudConsoleColor.RESET + output + CloudConsoleColor.RESET;
        }
        lineReader.getTerminal().puts(InfoCmp.Capability.carriage_return);
        lineReader.getTerminal().writer().println(output);
        lineReader.getTerminal().flush();
        if (lineReader.isReading()) {
            lineReader.callWidget(org.jline.reader.LineReader.REDRAW_LINE);
            lineReader.callWidget(org.jline.reader.LineReader.REDISPLAY);
        }
        return this;
    }

    @Override
    public ICloudConsole send(Object module, String output) {
        return this.send(module, output, Output.INFO);
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
        lineReader.getTerminal().reader().shutdown();
        lineReader.getTerminal().pause();
        this.thread.interrupt();
    }

    @Override
    public void print() {
        send(" ");
        System.out.println(" ");
        System.out.println("   ██████╗ ██╗   ██╗███████╗████████╗██╗   ██╗\n" +
                           "   ██╔══██╗██║   ██║██╔════╝╚══██╔══╝╚██╗ ██╔╝\n" +
                           "   ██████╔╝██║   ██║███████╗   ██║    ╚████╔╝ \n" +
                           "   ██╔══██╗██║   ██║╚════██║   ██║     ╚██╔╝  \n" +
                           "   ██║  ██║╚██████╔╝███████║   ██║      ██║   \n" +
                           "   ╚═╝  ╚═╝ ╚═════╝ ╚══════╝   ╚═╝      ╚═╝   \n" +
                           "                                           ");
        send(" ");
        send("      Contributors # §aGenerix030 §r- §aRedCrew");
        send("      Phase        # §ebeta §r- 1.0");
        send(" ");
    }

    @Override
    public ICloudConsole push(Consumer<String> handler) {
        this.handlers.push(handler);
        return this;
    }
}
