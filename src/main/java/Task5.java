import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class Task5 implements Task{
    private final String host;
    private final int fromPort;
    private final int toPort;
    private Thread scanThread;
    private volatile boolean running;

    public Task5(String host, int fromPort, int toPort) {
        this.host = host;
        this.fromPort = fromPort;
        this.toPort = toPort;
    }

    private void checkPort(int port) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), 200);
            System.out.println("Порт " + port + " открыт");
        } catch (IOException e) {
            // Порт закрыт
        }
    }
    @Override
    public void start() {
        if (running) { // Если сканирование уже выполняется
            return;
        }
        running = true;
        scanThread = new Thread(() -> {
            for (int port = fromPort; port <= toPort && running; port++) {
                checkPort(port);
                if (port == toPort) {
                    System.out.println("Сканирование завершено!");
                }
                try {
                    Thread.sleep(50); // Чтобы не перегружать сеть
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }

            running = false;
        });
        scanThread.start();
    }

    @Override
    public void stop() {
        running = false;
        if (scanThread != null) {
            scanThread.interrupt();
        }
        System.out.println("Сканирование остановлено!");
    }

    public static void main(String[] args) {
        Task5 task = new Task5("localhost", 1, 2000);
        System.out.println("Введенные значения: host - " + task.host + ", диапазон - [" + task.fromPort + ";" + task.toPort + "]");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Сканирование началось, введите stop для прекращения...");
        task.start();
        while (true) {
            if (scanner.nextLine().equals("stop")) {
                task.stop();
                break;
            } else {
                System.out.println("Неизвестная команда, введите stop для остановки");
            }
        }
        System.out.println("Программа завершена!");
    }
}
