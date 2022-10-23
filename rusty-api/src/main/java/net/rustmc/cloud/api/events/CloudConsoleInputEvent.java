package net.rustmc.cloud.api.events;

import lombok.Getter;
import net.rustmc.cloud.base.events.CloudEvent;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
@Getter
public final class CloudConsoleInputEvent extends CloudEvent {

    private final String input;
    private final String[] arguments;

    public CloudConsoleInputEvent(String input, String[] arguments) {
        this.input = input;
        this.arguments = input.split(" ");
    }

}
