package net.rustmc.cloud.base.common.console;

import lombok.SneakyThrows;
import net.rustmc.cloud.base.console.ICloudConsole;
import net.rustmc.cloud.base.console.ICloudConsoleFactory;
import org.fusesource.jansi.AnsiConsole;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
public class DefaultCloudConsoleFactoryImpl implements ICloudConsoleFactory {

    private final LineReader reader = newLineReader();

    @SneakyThrows
    public LineReader newLineReader() {
        final var terminal = TerminalBuilder.builder()
                .streams(System.in, System.out)
                .encoding(StandardCharsets.UTF_8)
                .system(true)
                .dumb(true)
                .build();
        return LineReaderBuilder.builder()
                .completer(new DefaultConsoleCompleter())
                .terminal(terminal)
                .option(LineReader.Option.DISABLE_EVENT_EXPANSION, true)
                .option(LineReader.Option.AUTO_REMOVE_SLASH, false)
                .option(LineReader.Option.INSERT_TAB, false).build();
    }

    @Override
    public LineReader getCloudLineReader() {
        return reader;
    }

    @Override
    public ICloudConsole newConsole() {
        return new DefaultCloudConsoleImpl();
    }

}
