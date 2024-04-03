import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class UserInterface {
    public static void main(String[] args) {
        // Pobranie danych z argumentów programu.
        final double learnRate = Double.parseDouble(args[0]); // Stała uczenia.
        final String trainSetPath = args[1]; // Zbiór treningowy.
        final String testSetPath = args[2]; // Zbiór testowy.

        Trainer trainer = new Trainer(); // Obiekt trainer.
        final List<String> trainSet = IOUtility.readSet(trainSetPath); // Przeczytanie pliku trainSet.
        final List<String> testSet = IOUtility.readSet(testSetPath); // Przeczytanie pliku testSet.
        final Integer dataVectorSize = trainer.getInputCount(trainSet); // Ilość danych wewnątrz pojedyńczego wektora z trainSet.
        final Perceptron perceptron = new Perceptron(dataVectorSize, learnRate); // Obiekt perceptron.

        Scanner scanner = new Scanner(System.in);

        do {
            System.out.println("Dostępne opcje : ");
            System.out.println("1. Klasyfikacja na w args train-set oraz test-set.");
            System.out.println("2. Wprowadzenie własnego wektora do testowania.");
            System.out.println("3. Zakończenie pracy programu.");

            System.out.print("Wprowadź opcje : ");
            final int i = scanner.nextInt();

            if (i == 1) {
                trainer.train(trainSet, perceptron); // Przeprowadzenie trenowania.
                perceptron.compute(testSet, trainer); // Przeprowadzenie klasyfikacji.
                perceptron.displayAccuracy(); // Wyświetlenie dokładności klasyfikacji.
            } else if (i == 2) {
                System.out.println(STR."Obowiązujący format to : \{perceptron.getVectorFormat()}");
                System.out.print("Wprowadź wektor : ");
                final String vector = scanner.next();

                trainer.train(trainSet, perceptron); // Przeprowadzenie trenowania.
                perceptron.compute(Collections.singletonList(vector), trainer); // Przeprowadzenie klasyfikacji (własny wektor).
                perceptron.displayAccuracy(); // Wyświetlenie dokładności klasyfikacji.
            } else if (i == 3) {
                return;
            } else {
                System.err.println("Taka opcja nie istnieje! Wprowadź numer od 1 do 3!");
            }
        }
        while (true);
    }
}
