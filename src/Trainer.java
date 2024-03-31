import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Trainer {
    private HashMap<String, Integer> labelToIntegerMap;

    /* Metoda czyta plik readSet/testSet. */
    public List<String> readSet(String trainSetPath) {
        List<String> strings = null;

        try {
            strings = Files.readAllLines(Path.of(trainSetPath));
        } catch (IOException e) {
            System.err.println(STR."Exception while reading the trainSetFile, at \{trainSetPath}");
        }

        return strings;
    }

    /* Metoda zakłada, że dane oddzielone są ";" (w konwencji pliku .csv). Dodatkowo zakładamy, że dana
     * wynikowa/oczekiwana w przeczytanym pliku jest zawsze innego typu niż numeryczna. */
    public Integer getInputCount(List<String> dataSet) {
        final String[] split = dataSet.getFirst().split(";");

        int inputCount = 0;
        for (String s : split) {
            if (s.matches("\\d+(\\.\\d+)?")) {
                inputCount += 1;
            }
        }

        return inputCount;
    }

    public void train(List<String> trainSet, Perceptron perceptron) {
        // Zamiana etykiet na cyfry (mamy tylko dwie klasy, więc zakładamy,
        // że etykiety to 0 lub 1). Zamiana dzieje się na zasadzie "śledzenia" poprzednich etykiet.
        labelsToIntegerStrings(trainSet);

        // Umieszczenie wektorów wraz z etykietami w trainMap.
        HashMap<double[], Integer> trainMap = new HashMap<>();

        for (String string : trainSet) {
            final String[] split = string.split(";");

            double[] vector = new double[split.length - 1];
            final String label = split[split.length - 1];

            for (int i = 0; i < split.length - 1; i++) {
                vector[i] = Double.parseDouble(split[i]);
            }

            trainMap.put(vector, labelToIntegerMap.get(label));
        }

        // Obliczenie wyjścia na podstawie aktualnych wag oraz następującej
        // funkcji wyjścia (y >= 0 -> net = 1, y < 0 -> net = 0), wraz ze
        // sprawdzeniem zgodności wyjścia i przeprowadzeniem reguły Delta.
        for (Map.Entry<double[], Integer> entry : trainMap.entrySet()) {
            // Iloczyn wektorowy.
            double y = 0;
            for (int i = 0; i < entry.getKey().length; i++) {
                y += (entry.getKey()[i] * perceptron.weights[i]);
            }
            y -= perceptron.tVal;

            // Sprawdzenie, czy wymagane jest użycie reguły delta.
            int net = y >= 0 ? 1 : 0;
            if (net != entry.getValue()) {
                perceptron.deltaRule(net, entry.getValue(), entry.getKey());
            }
        }
    }

    private void labelsToIntegerStrings(List<String> trainSet) {
        HashSet<String> set = new HashSet<>();

        for (String string : trainSet) {
            final String[] split = string.split(";");
            set.add(split[split.length - 1]);
        }

        if (set.size() != 2) {
            System.err.println(STR."Class count, can only be *2* for one perceptron! It is currently \{set.size()}!");
        } else {
            final List<String> list = set.stream().toList();
            labelToIntegerMap = new HashMap<>();
            labelToIntegerMap.put(list.get(0), 0);
            labelToIntegerMap.put(list.get(1), 1);
        }
    }
}
