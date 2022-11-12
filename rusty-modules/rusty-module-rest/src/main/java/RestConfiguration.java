import lombok.Getter;
import net.rustmc.cloud.base.configuration.CloudConfiguration;
import net.rustmc.cloud.base.configuration.CloudConfigurationInfo;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 12.11.2022
 */
@Getter
@CloudConfigurationInfo(name = "test")
public class RestConfiguration implements CloudConfiguration {

    private String host;
    private int port;


}
