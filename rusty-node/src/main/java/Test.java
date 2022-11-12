import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * This class belongs to the rusty-cloud project
 *
 * @author Alexander Jilge
 * @since 08.11.2022
 */
public class Test {

    private static List<Consumer<Integer>> list = new LinkedList<>();

    public static void main(String[] args) {

        list.add(w -> {

        });

        for (Consumer<Integer> consumer : list) {
            consumer.accept(1);
        }

    }


}
