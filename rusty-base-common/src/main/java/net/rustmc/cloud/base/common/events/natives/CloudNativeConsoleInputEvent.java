package net.rustmc.cloud.base.common.events.natives;

import lombok.Getter;
import net.rustmc.cloud.base.events.CloudEvent;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
@Getter
public final class CloudNativeConsoleInputEvent extends CloudEvent {

    private final String input;
    private final String[] arguments;

    public CloudNativeConsoleInputEvent(String input) {
        this.input = input;
        this.arguments = input.split(" ");
    }

}
