import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Trainer {
    private Integer inputCount;

    public List<String> readSet(String trainSetPath) {
        List<String> strings = null;

        try {
            strings = Files.readAllLines(Path.of(trainSetPath));
        } catch (IOException e) {
            System.out.println(STR."Exception while reading the trainSetFile, at \{trainSetPath}");
        }

        return strings;
    }

    /* Metoda zakłada, że dane oddzielone są ";" (w konwencji pliku .csv). */
    public Integer getInputCount(List<String> strings) {
        final String[] split = strings.get(0).split(";");

        int inputCount = 0;
        for (String s : split) {
            if (s.matches("\\d+(\\.\\d+)?")) {
                inputCount += 1;
            }
        }

        return inputCount;
    }


}
