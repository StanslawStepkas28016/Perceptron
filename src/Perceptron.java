import java.util.*;

public class Perceptron {
    private final Integer dataVectorSize;
    public final Double learnRate;
    public Double tVal;
    private Integer hit = 0;
    private Integer totalDataSize = 0;
    public double[] weights;

    public Perceptron(Integer dataVectorSize, Double learnRate) {
        this.dataVectorSize = dataVectorSize;
        this.learnRate = learnRate;
        this.tVal = 1.0; // Defaultowo 1, zmieniane, przy okazji deltaRule.

        weights = new double[dataVectorSize];
        Random random = new Random();

        // Inicjalizacja wektora wag, wagami z zakresu <-5,5>.
        for (double weight : weights) {
            weight = random.nextDouble(-5, 5);
        }
    }

    /* Metoda służy do obliczenia wyjścia y perceptronu. */
    public void compute(List<String> testSet, Trainer utilityTrainer) {
        final HashMap<double[], Integer> labeledMap = utilityTrainer.getLabeledMap(testSet);

        // Obliczenie net, razem z "transformacją" netu przez funkcję wyjścia.
        for (Map.Entry<double[], Integer> entry : labeledMap.entrySet()) {
            double net = 0.0;
            final double[] data = entry.getKey();

            for (int i = 0; i < data.length; i++) {
                net += data[i] * weights[i];
            }

            net -= tVal;
            int y = net >= 0 ? 1 : 0;

            if (y == entry.getValue()) {
                hit += 1;
            }

            totalDataSize = labeledMap.size();

            System.out.println(STR."\{Arrays.toString(entry.getKey())}, expected : \{entry.getValue()}, computed : \{y}");
        }

    }

    /* Metoda służy do zastosowania reguły DELTA. */
    public void deltaRule(Integer receivedValue, Integer expectedValue, double[] inputVector) {
        double[] oldWeights = weights;
        double[] newWeights = new double[dataVectorSize];

        // Prawa część wzoru W’=W+(d-y)𝛼X, konkretnie (d-y)𝛼X.
        for (int i = 0; i < inputVector.length; i++) {
            inputVector[i] = (expectedValue - receivedValue) * learnRate * inputVector[i];
        }
        // Suma starego wektora oldWeights z przerobionym (po prawej części wzoru) inputVector.
        for (int i = 0; i < newWeights.length; i++) {
            newWeights[i] = inputVector[i] + oldWeights[i];
        }

        // Wyliczenie nowego t (t’= t -(d-y)𝛼).
        tVal = tVal - (expectedValue - receivedValue) * learnRate;

        // Inicjalizacja pola weights nowym wektorem wag.
        weights = newWeights;
    }

    public Double getAccuracy() {
        return hit / (double) totalDataSize;
    }
}
