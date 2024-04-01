import java.util.List;

public class UserInterface {
    public static void main(String[] args) {
        // Pobranie danych z argumentów programu.
        final double learnRate = Double.parseDouble(args[0]); // Stała uczenia.
        final String trainSetPath = args[1]; // Zbiór treningowy.
        final String testSetPath = args[2]; // Zbiór testowy.

        // Trenowanie na zbiorze trainSet.
        Trainer trainer = new Trainer(); // Obiekt trainer.
        final List<String> trainSet = IOUtility.readSet(trainSetPath); // Przeczytanie pliku trainSet.
        final Integer dataVectorSize = trainer.getInputCount(trainSet); // Ilość danych wewnątrz pojedyńczego wektora z trainSet.
        final Perceptron perceptron = new Perceptron(dataVectorSize, learnRate); // Obiekt perceptron.
        trainer.train(trainSet, perceptron); // Przeprowadzenie trenowania.

        // Przeprowadzenie klasyfikacji.
        final List<String> testSet = IOUtility.readSet(testSetPath);
        perceptron.compute(testSet, trainer);
        perceptron.displayAccuracy();
    }
}
