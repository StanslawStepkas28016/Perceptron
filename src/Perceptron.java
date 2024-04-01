import java.util.List;
import java.util.Random;

public class Perceptron {
    private final Integer inputSize;
    public final Double learnRate;
    public Double tVal;
    public double[] weights;

    public Perceptron(Integer inputSize, Double learnRate) {
        this.inputSize = inputSize;
        this.learnRate = learnRate;
        this.tVal = 1.0; // Defaultowo 1, zmieniane, przy okazji deltaRule.

        weights = new double[inputSize];
        Random random = new Random();

        // Inicjalizacja wektora wag, wagami z zakresu <-5,5>.
        for (double weight : weights) {
            weight = random.nextDouble(-5, 5);
        }
    }

    /* Metoda służy do obliczenia wyjścia y perceptronu. */
    public void compute(List<String> testSet) {

    }

    /* Metoda służy do zastosowania reguły DELTA. */
    public void deltaRule(Integer receivedValue, Integer expectedValue, double[] inputVector) {
        double[] oldWeights = weights;
        double[] newWeights = new double[inputSize];

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
}
