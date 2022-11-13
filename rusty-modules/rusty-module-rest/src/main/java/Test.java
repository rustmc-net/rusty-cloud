import net.rustmc.cloud.base.common.Rust;
import net.rustmc.cloud.master.modules.RustyCloudModule;

import java.io.File;
import java.util.Set;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 12.11.2022
 */
public class Test extends RustyCloudModule {

    private RestConfiguration configuration = Rust.getInstance().getConfigurationHandler().open(new File("test.json").toURI(), RestConfiguration.class);

    @Override
    public void onBoot() {

        configuration.getPort();

        Rust.getInstance().getConfigurationHandler().update("test");

    }

    @Override
    public void onTerminate() {

    }
}
