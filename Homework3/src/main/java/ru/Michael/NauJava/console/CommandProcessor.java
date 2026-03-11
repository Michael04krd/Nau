package ru.Michael.NauJava.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.Michael.NauJava.entity.Product;
import ru.Michael.NauJava.service.ProductService;

import java.util.List;

@Component
public class CommandProcessor {
    private final ProductService productService;

    @Autowired
    public CommandProcessor(ProductService productService) {
        this.productService = productService;
    }

    public void processCommand(String input) {
        String[] parts = input.split(" ");
        String command = parts[0];

        try {
            switch (command) {
                case "add":
                    handleAdd(parts);
                    break;
                case "get":
                    handleGet(parts);
                    break;
                case "category":
                    handleCategory(parts);
                    break;
                case "cheaper":
                    handleCheaper(parts);
                    break;
                case "price":
                    handlePrice(parts);
                    break;
                case "sell":
                    handleSell(parts);
                    break;
                case "remove":
                    handleRemove(parts);
                    break;
                case "help":
                    printHelp();
                    break;
                default:
                    System.out.println("Неизвестная команда. Введите 'help'");
            }
        } catch (Exception e) {
            System.out.println("Ошибка при выполнении команды: " + e.getMessage());
            System.out.println("Используйте 'help' для справки.");
        }
    }

    private void handleAdd(String [] args) {
        // add 1 Холодильник техника 30000 5
        if (args.length < 6) {
            System.out.println("Введите: add <id> <название> <категория> <цена> <количество>");
            return;
        }

        Long id = Long.parseLong(args[1]);
        String name = args[2];
        String category = args[3];
        double price = Double.parseDouble(args[4]);
        int quantity = Integer.parseInt(args[5]);

        productService.addProduct(id, name, category, price, quantity);
    }

    private void handleGet(String[] args) {
        // get 1
        if (args.length < 2) {
            System.out.println("Введите: get <id>");
            return;
        }

        Long id = Long.parseLong(args[1]);
        Product product = productService.findById(id);
        if (product != null) {
            System.out.println(product);
            return;
        }
        System.out.println("Продукт по такому ID не найден!");
    }

    private void handleCategory(String[] args) {
        // category электроника
        if (args.length < 2) {
            System.out.println("Введите: category <название_категории>");
        }
        String category = args[1];
        List<Product> categoryList = productService.findByCategory(category);
        if (categoryList.isEmpty()) {
            System.out.println("Нет объектов в этой категории!");
        } else {
            System.out.println("Объекты в выбранной категории:");
            categoryList.forEach(System.out::println);
        }
    }

    private void handleCheaper (String[] args) {
        // cheaper 20000
        if (args.length < 2) {
            System.out.println("Введите: cheaper <цена>");
        }
        double price = Double.parseDouble(args[1]);
        List<Product> cheaperList = productService.findCheaperThan(price);
        if (cheaperList.isEmpty()) {
            System.out.println("Товары дешевле " + price + " не найдены");
        } else {
            System.out.println("Товары дешевле " + price + ":");
            cheaperList.forEach(System.out::println);
        }
    }

    private void handlePrice(String[] args) {
        // price 1 20000
        if (args.length < 3) {
            System.out.println("Введите: price <ID> <цена>");
        }
        Long id = Long.parseLong(args[1]);
        double price = Double.parseDouble(args[2]);
        productService.updatePrice(id, price);
        System.out.println("Цена товара обновлена");
    }

    private void handleSell(String[] args) {
        // sell 1 2
        if (args.length < 3) {
            System.out.println("Введите: sell <id> <количество>");
            return;
        }
        Long id = Long.parseLong(args[1]);
        int quantity = Integer.parseInt(args[2]);

        productService.sellProduct(id, quantity);
    }

    private void handleRemove(String[] args) {
        // remove 1
        if (args.length < 2) {
            System.out.println("Введите: remove <id>");
            return;
        }

        Long id = Long.parseLong(args[1]);
        productService.deleteProduct(id);
        System.out.println("Товар удален");
    }

    private void printHelp() {
        System.out.println("Доступные команды:");
        System.out.println("  add <id> <название> <категория> <цена> <количество> - добавить товар");
        System.out.println("  get <id> - показать товар по id");
        System.out.println("  category <категория> - показать все товары категории");
        System.out.println("  cheaper <цена> - показать товары дешевле указанной цены");
        System.out.println("  price <id> <новая цена> - изменить цену товара");
        System.out.println("  sell <id> <количество> - продать товар");
        System.out.println("  remove <id> - удалить товар");
        System.out.println("  help - показать эту справку");
        System.out.println("  exit - выйти");
    }
}
