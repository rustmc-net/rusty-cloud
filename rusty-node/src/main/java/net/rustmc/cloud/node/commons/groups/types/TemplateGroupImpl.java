package net.rustmc.cloud.node.commons.groups.types;

import net.rustmc.cloud.base.common.Rust;
import net.rustmc.cloud.base.objects.SimpleCloudGroup;
import net.rustmc.cloud.base.util.FileHelper;
import net.rustmc.cloud.node.RustCloud;
import net.rustmc.cloud.node.commons.groups.Group;

import java.io.File;
import java.io.IOException;

public class TemplateGroupImpl extends Group {

    public TemplateGroupImpl(SimpleCloudGroup cloudGroup) {
        super(cloudGroup);
    }

    @Override
    public void shutdown() {

    }

    @Override
    public String getPath() {
        return "templates//" + this.cloudGroup.getName();
    }

}
