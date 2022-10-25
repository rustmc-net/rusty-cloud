package net.rustmc.cloud.master.exceptions;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 25.10.2022
 */
public class EmptyInstanceDescriptor extends UnsupportedOperationException {

    public EmptyInstanceDescriptor(final String module) {
        super("The module to be loaded is not described: " + module);
    }
}
