import net.rustmc.cloud.base.common.Rust;
import net.rustmc.cloud.base.console.CloudConsoleColor;
import net.rustmc.cloud.base.console.ICloudConsole;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 23.10.2022
 */
public class Main {

    public static void main(String[] args) {

        ICloudConsole console = Rust.getInstance().getConsoleFactory().newConsole();
        console.run();
        console.send("Hello World" + CloudConsoleColor.RESET +  "!", ICloudConsole.Output.ERROR);

    }

}
