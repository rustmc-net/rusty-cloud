package net.rustmc.cloud.master.common.nodes;

import lombok.SneakyThrows;
import net.rustmc.cloud.base.common.Rust;
import net.rustmc.cloud.master.configurations.CloudNodeConfiguration;
import net.rustmc.cloud.master.nodes.IOfflineNodeTerminal;
import net.rustmc.cloud.master.nodes.IOfflineNode;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 13.11.2022
 */
public class CloudOfflineNodeTerminalImpl implements IOfflineNodeTerminal {

    private final List<IOfflineNode> offlineNodes = new ArrayList<>();
    private final Random random = new Random();

    @Override
    public IOfflineNode registerCloudNodeConfiguration(File file) {
        if (file.getName().contains(".json")) {
            final CloudNodeConfiguration configuration = Rust.getInstance()
                    .getConfigurationHandler()
                    .open(file.getName()
                            .replace(".json", ""),
                            file.toURI(),
                            CloudNodeConfiguration.class
                    );
            final SimpleOfflineNode offlineNode = new SimpleOfflineNode(configuration, file);
            this.offlineNodes.add(offlineNode);
            return offlineNode;
        } else return null;
    }

    @Override
    public IOfflineNode getOfflineNodeByName(String name) {
        for (IOfflineNode node : this.offlineNodes) {
            if (node.configuration().getName().equals(name))
                return node;
        }
        return null;
    }

    @Override
    public IOfflineNode getOfflineNodeByNodeKey(int nodeKey) {
        for (IOfflineNode node : this.offlineNodes) {
            if (node.configuration().getNodeKey()  == nodeKey)
                return node;
        }
        return null;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @SneakyThrows
    @Override
    public IOfflineNode newOfflineNode(String name, int maxGroups) {
        final CloudNodeConfiguration configuration = new CloudNodeConfiguration(name, maxGroups, random.nextInt());
        final File file = new File("nodes//" + name + ".json");
        file.createNewFile();
        Rust.getInstance().getConfigurationHandler().open(name, file.toURI(), configuration);
        return new SimpleOfflineNode(configuration, file);
    }

    @Override
    public Collection<IOfflineNode> getOfflineNodes() {
        return this.offlineNodes;
    }

}
