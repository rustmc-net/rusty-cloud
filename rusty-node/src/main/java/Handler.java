import java.util.function.Consumer;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 08.11.2022
 */
public class Handler {

    public Handler(Consumer<String> consumer) {

        consumer.accept("Test");

    }
}
