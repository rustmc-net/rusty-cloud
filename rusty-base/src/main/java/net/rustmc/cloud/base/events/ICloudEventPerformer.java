package net.rustmc.cloud.base.events;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
public interface ICloudEventPerformer {

    public <T extends CloudEvent> void perform(T event);

}
