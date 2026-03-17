package ru.Michael.NauJava.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;

@Component
public class AppInfoPrinter {

    @Value("${app.name}")
    private String appName;

    @Value("${app.version}")
    private String appVersion;

    @PostConstruct
    public void printAppInfo() {
        System.out.println("Запущено приложение: " + appName);
        System.out.println("Версия: " + appVersion);
        System.out.println("----------------------------------\n");
    }
}