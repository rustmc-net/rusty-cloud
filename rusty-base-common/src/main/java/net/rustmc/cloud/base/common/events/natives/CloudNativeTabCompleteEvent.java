package net.rustmc.cloud.base.common.events.natives;

import lombok.Getter;
import net.rustmc.cloud.base.events.CloudEvent;
import org.jline.reader.Candidate;

import java.util.ArrayList;
import java.util.List;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
@Getter
public final class CloudNativeTabCompleteEvent extends CloudEvent {

    private static final List<Candidate> candidates = new ArrayList<>();

    private final List<String> words;
    private final int index;
    private final String line;

    public CloudNativeTabCompleteEvent(List<String> words, int index, String line) {
        this.words = words;
        this.index = index;
        this.line = line;
    }

    public List<String> getWords() {
        return words;
    }

    public void prompt(final String prompt) {
        candidates.add(new Candidate(prompt));
    }

    public static void flush() {
        candidates.clear();
    }

}
