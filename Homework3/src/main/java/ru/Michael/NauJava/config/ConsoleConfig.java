package ru.Michael.NauJava.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.Michael.NauJava.console.CommandProcessor;

import java.util.Scanner;

@Configuration
public class ConsoleConfig {

    @Autowired
    private CommandProcessor commandProcessor;

    @Bean
    public CommandLineRunner commandLineRunner() {
        return args -> {
            try (Scanner scanner = new Scanner(System.in)) {
                System.out.println("Добро пожаловать в интернет-магазин!");
                System.out.println("Введите 'help' для списка команд");
                System.out.println("-----------------------------------");

                while (true) {
                    System.out.print("> ");
                    String input = scanner.nextLine().trim();

                    if ("exit".equalsIgnoreCase(input)) {
                        System.out.println("До свидания!");
                        break;
                    }

                    if (!input.isEmpty()) {
                        commandProcessor.processCommand(input);
                    }
                }
            }
        };
    }
}