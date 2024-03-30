import java.util.List;

public class UserInterface {
    public static void main(String[] args) {
        // Pobranie danych z argumentów programu.
        final double a = Double.parseDouble(args[0]); // Stała uczenia.
        final String trainSetPath = args[1]; // Zbiór treningowy.
        final String testSetPath = args[2]; // Zbiór testowy.

        // Trenowanie na zbiorze trainSet.
        Trainer trainer = new Trainer(); // Obiekt trainer.
        final List<String> trainSet = trainer.readSet(trainSetPath); // Przeczytanie pliku trainSet.
        final Integer inputCount = trainer.getInputCount(trainSet); // Ilość danych wewnątrz pojedyńczego wektora z trainSet.

        final Perceptron perceptron = new Perceptron(inputCount, a);
        

    }
}
