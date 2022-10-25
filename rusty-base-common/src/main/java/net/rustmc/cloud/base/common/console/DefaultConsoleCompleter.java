package net.rustmc.cloud.base.common.console;

import net.rustmc.cloud.base.common.Rust;
import net.rustmc.cloud.base.common.events.natives.CloudNativeTabCompleteEvent;
import org.jline.reader.Candidate;
import org.jline.reader.Completer;
import org.jline.reader.LineReader;
import org.jline.reader.ParsedLine;

import java.util.List;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
public class DefaultConsoleCompleter implements Completer {

    @Override
    public void complete(LineReader lineReader, ParsedLine parsedLine, List<Candidate> list) {
        Rust.getInstance().getEventPerformer().perform(new CloudNativeTabCompleteEvent(parsedLine.words(), parsedLine.wordIndex(), parsedLine.line()));
        list.addAll(CloudNativeTabCompleteEvent.getCandidates());
        CloudNativeTabCompleteEvent.flush();
    }

}
