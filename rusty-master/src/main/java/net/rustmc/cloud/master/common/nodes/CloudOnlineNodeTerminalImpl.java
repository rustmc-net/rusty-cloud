package net.rustmc.cloud.master.common.nodes;

import net.rustmc.cloud.base.communicate.ICommunicateChannel;
import net.rustmc.cloud.master.nodes.IOfflineNode;
import net.rustmc.cloud.master.nodes.IOnlineNode;
import net.rustmc.cloud.master.nodes.IOnlineNodeTerminal;

import java.util.ArrayList;
import java.util.List;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 21.11.2022
 */
public class CloudOnlineNodeTerminalImpl implements IOnlineNodeTerminal {

    private final List<IOnlineNode> nodes = new ArrayList<>();

    @Override
    public void open(IOfflineNode offlineNode, ICommunicateChannel communicateChannel) {
        this.nodes.add(new OnlineNodeImpl(offlineNode, communicateChannel));
    }

    @Override
    public IOnlineNode getByName(String name) {
        for (IOnlineNode node : this.nodes) {
            if (node.configuration().getName().equals(name)) return node;
        }
        return null;
    }

    @Override
    public IOnlineNode getByNodeKey(int nodeKey) {
        for (IOnlineNode node : this.nodes) {
            if (node.configuration().getNodeKey() == nodeKey) return node;
        }
        return null;
    }

    @Override
    public IOnlineNode getByUniqueID(String uniqueID) {
        for (IOnlineNode node : this.nodes) {
            if (node.getNodeCommunicateChannel().getUniqueID().equals(uniqueID)) return node;
        }
        return null;
    }

    @Override
    public void remove(String name) {
        int pos = 0;
        for (int i = 0; i < this.nodes.size(); i++) {
            final var node = this.nodes.get(i);
            if (node.configuration().getName().equals(name)) {
                pos = i;
                break;
            }
        }
        this.nodes.remove(pos);
    }

    @Override
    public int size() {
        return this.nodes.size();
    }

    @Override
    public List<IOnlineNode> getOnlineNodes() {
        return this.nodes;
    }

}
