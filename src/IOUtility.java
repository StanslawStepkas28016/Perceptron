import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class IOUtility {

    /* Metoda czyta plik readSet/testSet. */
    public static List<String> readSet(String trainSetPath) {
        List<String> strings = null;

        try {
            strings = Files.readAllLines(Path.of(trainSetPath));
        } catch (IOException e) {
            System.err.println(STR."Exception while reading the trainSetFile, at \{trainSetPath}");
        }

        return strings;
    }
}
