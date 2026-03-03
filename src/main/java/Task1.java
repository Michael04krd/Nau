import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Random;

public class Task1 {
    private final int[] numbers;
    private final Random random = new Random();

    public Task1(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Длина массива должна быть положительной!");
        }
        numbers = new int[n];
        initialize();
    }

    public static void main(String[] args) {
        Task1 task = new Task1(7);
        if (task.numbers.length > 0) {
            System.out.println("Массив: ");
            task.printArray();
            System.out.println("\nМаксимальное значение по модулю: " + task.maxValue());
            System.out.println("Минимальное значение по модулю: " + task.minValue());
            System.out.println("Среднее значение в массиве: " + task.middleValue());
            System.out.println("Последний положительный в массиве: " + task.lastPositive());
            System.out.println("Сумма положительных элементов в массиве: " + task.sumPositive());
        } else {
            System.out.println("Массив пустой, смысла в заданиях нет");
        }
    }

    public void printArray() {
        System.out.println(Arrays.toString(numbers));
    }

    private void initialize() {
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = random.nextInt();
        }
    }

    private void checkNotEmpty() {
        if (numbers.length == 0) {
            throw new IllegalStateException("Массив пуст");
        }
    }

    public int maxValue() {
        checkNotEmpty();
        int max = 0;
        for (int number : numbers) {
            if (Math.abs(number) > max) {
                max = Math.abs(number);
            }
        }
        return max;
    }

    public int minValue() {
        checkNotEmpty();
        int min = Integer.MAX_VALUE;
        for (int number : numbers) {
            if (Math.abs(number) < min) {
                min = Math.abs(number);
            }
        }
        return min;
    }

    public double middleValue() {
        checkNotEmpty();
        double sum = 0;
        for (int number : numbers) {
            sum += number;
        }
        return sum / numbers.length;
    }

    public int lastPositive() throws NoSuchElementException {
        checkNotEmpty();
        for (int i = numbers.length - 1; i >= 0; i--) {
            if (numbers[i] > 0) {
                return numbers[i];
            }
        }
        throw new NoSuchElementException("В массиве нет положительного числа!");
    }

    public int sumPositive() {
        if (numbers.length == 0) {
            return 0;
        }
        int sum = 0;
        for (int number : numbers) {
            if (number > 0) {
                sum += number;
            }
        }
        return sum;
    }
}