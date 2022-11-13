package net.rustmc.cloud.master.configurations;

import lombok.Getter;
import net.rustmc.cloud.base.configuration.CloudConfiguration;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 13.11.2022
 */
@Getter
@SuppressWarnings("FieldMayBeFinal")
public class CloudNodeConfiguration implements CloudConfiguration {

    private String name;
    private int maxGroups;
    private int nodeKey;

    public CloudNodeConfiguration(String name, int maxGroups, int nodeKey) {
        this.name = name;
        this.maxGroups = maxGroups;
        this.nodeKey = nodeKey;
    }

}
