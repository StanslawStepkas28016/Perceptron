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

        Scanner scanner = new Scanner(System.in); // Wejście.
        boolean train = true;

        do {
            displayOptions();
            System.out.print("Wprowadź opcje : ");
            final int i = scanner.nextInt();

            if (train) {
                trainer.train(trainSet, perceptron); // Przeprowadzenie trenowania.
            }

            if (i == 1) {
                argsClassification(perceptron, testSet, trainer);
                train = nextTrain(train);
            } else if (i == 2) {
                inputClassification(perceptron, scanner, trainer);
            } else if (i == 3) {
                return;
            } else {
                System.err.println("Taka opcja nie istnieje! Wprowadź numer od 1 do 3!");
            }
        }
        while (true);
    }

    private static boolean nextTrain(boolean train) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Czy chcesz przeprowadzić kolejny potok nauczania? (0 - Nie, 1 - Tak) : ");
        final int nextInt = sc.nextInt();

        if (nextInt == 1) {
            train = false;
            System.out.println("Po wybraniu opcji z menu, zostanie przeprowadzony kolejny potok!");
        }

        System.out.println();

        return train;
    }

    private static void displayOptions() {
        System.out.println("Dostępne opcje : ");
        System.out.println("1. Klasyfikacja na w args train-set oraz test-set.");
        System.out.println("2. Wprowadzenie własnego wektora do testowania.");
        System.out.println("3. Zakończenie pracy programu.");
    }

    private static void inputClassification(Perceptron perceptron, Scanner scanner, Trainer utilityTrainer) {
        System.out.println();
        System.out.println(STR."Obowiązujący format to : \{perceptron.getVectorFormat()}");
        System.out.print("Wprowadź wektor : ");
        final String vector = scanner.next(); // Wejście wektora.
        perceptron.computeForInputVector(vector, utilityTrainer); // Przeprowadzenie klasyfikacji (własny wektor).
        System.out.println();
    }

    private static void argsClassification(Perceptron perceptron, List<String> testSet, Trainer trainer) {
        System.out.println();
        perceptron.compute(testSet, trainer); // Przeprowadzenie klasyfikacji.
        perceptron.displayAccuracy(); // Wyświetlenie dokładności klasyfikacji.
        System.out.println();
    }
}
