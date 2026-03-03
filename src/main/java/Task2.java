import java.util.ArrayList;
import java.util.Random;

public class Task2 {
    private final ArrayList<Double> arrayList;
    private final Random random = new Random();

    public Task2(int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Длина массива должна быть положительной!");
        }
        arrayList = new ArrayList<>(n);
        initialize(n);
    }

    public static void main(String[] args) {
        Task2 task = new Task2(6);
        if (task.arrayList.isEmpty()) {
            System.out.println("Список пуст");
            return;
        }
        System.out.println("До сортировки:");
        task.printArray();
        task.insertionSort();
        System.out.println("После сортировки:");
        task.printArray();
    }

    private void initialize(int n) {
        for (int i = 0; i < n; i++) {
            arrayList.add(random.nextDouble());
        }
    }

    public void printArray() {
        System.out.println(arrayList);
    }

    public void insertionSort() {
        for (int i = 1; i < arrayList.size(); i++) {
            double next = arrayList.get(i);
            int j = i - 1;
            while (j >= 0 && arrayList.get(j) > next) {
                arrayList.set(j + 1, arrayList.get(j));
                j--;
            }
            arrayList.set(j + 1, next);
        }
    }
}