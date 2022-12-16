package net.rustmc.cloud.node.commons.groups.types;

import net.rustmc.cloud.base.objects.SimpleCloudGroup;
import net.rustmc.cloud.node.commons.groups.Group;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class StaticGroupImpl extends Group {

    public StaticGroupImpl(SimpleCloudGroup cloudGroup) {
        super(cloudGroup);
    }

    @Override
    public void shutdown() {
        /* todo: save data */
        for (Process task : this.tasks) {
            try {
                task.getOutputStream().write("stop".getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                task.destroy();
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public String getPath() {
        return "statics//" + this.cloudGroup.getName();
    }

}
