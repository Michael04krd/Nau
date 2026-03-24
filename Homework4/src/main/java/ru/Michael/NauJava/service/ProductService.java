package ru.Michael.NauJava.service;

import ru.Michael.NauJava.entity.Product;
import java.util.List;

// Сервис для работы с товарами. Тут основная бизнес-логика работы интернет-магазина

public interface ProductService {
    void addProduct(String name, String category, double price, int quantity);

    Product findById(Long id);

    List<Product> findByCategory(String category);

    List<Product> findCheaperThan(double price);

    void updatePrice(Long id, double newPrice);

    void sellProduct(Long id, int quantity);

    void deleteProduct(Long id);
}
