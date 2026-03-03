import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nДомашняя работа №2. Выберете программу:");
            System.out.println("1. Задание 1");
            System.out.println("2. Задание 2");
            System.out.println("3. Задание 3");
            System.out.println("4. Задание 4");
            System.out.println("5. Задание 5");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    Task1.main(args);
                    break;
                case 2:
                    Task2.main(args);
                    break;
                case 3:
                    Employee.main(args);
                    break;
                case 4:
                    Task4.main(args);
                    break;
                case 5:
                    Task5.main(args);
                    break;
                default:
                    System.out.println("Неверный выбор!");
            }
        }
    }
}
