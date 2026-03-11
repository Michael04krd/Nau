package ru.Michael.NauJava.config;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import ru.Michael.NauJava.entity.Product;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class AppConfig {
    @Bean
    @Scope(value = BeanDefinition.SCOPE_SINGLETON)
    public List<Product> productContainer() {
        return new ArrayList<>();
    }
}
