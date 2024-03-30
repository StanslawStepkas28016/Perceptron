import java.util.Random;

public class Perceptron {
    private final Integer inputSize;
    private final Double learnRate;
    public double[] weights;

    public Perceptron(Integer inputSize, Double learnRate) {
        this.inputSize = inputSize;
        this.learnRate = learnRate;

        weights = new double[inputSize];
        Random random = new Random();

        // Inicjalizacja wektora wag, wagami z zakresu <-5,5>.
        for (double weight : weights) {
            weight = random.nextDouble(-5, 5);
        }
    }

    /* Metoda służy do obliczenia wyjścia y perceptronu. */
    public void compute() {

    }

    /* Metoda służy do zastosowania reguły DELTA. */
    public void deltaRule() {
    }
}
