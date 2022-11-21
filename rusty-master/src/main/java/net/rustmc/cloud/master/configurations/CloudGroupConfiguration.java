package net.rustmc.cloud.master.configurations;

import lombok.Getter;
import net.rustmc.cloud.base.configuration.CloudConfiguration;
import net.rustmc.cloud.base.objects.SimpleCloudGroup;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 13.11.2022
 */
@SuppressWarnings("FieldMayBeFinal")
@Getter
public record CloudGroupConfiguration(SimpleCloudGroup cloudGroup) implements CloudConfiguration {
}