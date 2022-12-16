package net.rustmc.cloud.node.commons.groups.types;

import net.rustmc.cloud.base.objects.SimpleCloudGroup;
import net.rustmc.cloud.node.commons.groups.Group;
import net.rustmc.cloud.node.service.INativeOnlineService;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class StaticGroupImpl extends Group {

    public StaticGroupImpl(SimpleCloudGroup cloudGroup) {
        super(cloudGroup);
    }

    @Override
    public void shutdown() {
        /* todo: save data */
        for (INativeOnlineService service : this.services) {
            service.shutdown();
        }
    }

    @Override
    public String getPath() {
        return "statics//" + this.cloudGroup.getName();
    }

}
