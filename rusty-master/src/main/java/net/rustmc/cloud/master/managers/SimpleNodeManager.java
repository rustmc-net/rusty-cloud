package net.rustmc.cloud.master.managers;

import net.rustmc.cloud.api.objects.SimpleCloudNode;
import net.rustmc.cloud.base.common.Rust;
import net.rustmc.cloud.master.RustCloud;
import net.rustmc.cloud.master.configurations.RustyNodeConfiguration;

import java.io.File;

/**
 * this file belongs to the rusty-cloud project.
 *
 * @author Alexander Jilge
 * @since 24.10.2022, Mo.
 */
public final class SimpleNodeManager {

    public void register(final SimpleCloudNode cloudNode) {
        final var nodeConfiguration = new RustyNodeConfiguration(cloudNode);
        Rust.getInstance()
                .getConfigurationHandler()
                .open(
                        cloudNode.getName(),
                        new File("nodes//" + cloudNode.getName() + ".json").toURI(),
                        new RustyNodeConfiguration(cloudNode)
                );
        RustCloud.getCloud().getNodeConfigurations().add(nodeConfiguration);
    }

    public boolean containsNodeKey(int nodeKey) {
        for (RustyNodeConfiguration configuration : RustCloud.getCloud().getNodeConfigurations()) {
            if (configuration.getNode().getNodeKey() == nodeKey)
                return true;
        }
        return false;
    }

    public String getNameOfNodeKey(int nodeKey) {
        for (RustyNodeConfiguration configuration : RustCloud.getCloud().getNodeConfigurations()) {
            if (configuration.getNode().getNodeKey() == nodeKey)
                return configuration.getNode().getName();
        }
        return "null";
    }

    public SimpleCloudNode getNodeByNodeKey(int nodeKey) {
        for (RustyNodeConfiguration configuration : RustCloud.getCloud().getNodeConfigurations()) {
            if (configuration.getNode().getNodeKey() == nodeKey)
                return configuration.getNode();
        }
        return null;
    }

    public SimpleCloudNode getNodeByNodeAddress(String address) {
        for (RustyNodeConfiguration configuration : RustCloud.getCloud().getNodeConfigurations()) {
            if (configuration.getNode().getHost().equals(address))
                return configuration.getNode();
        }
        return null;
    }

}
