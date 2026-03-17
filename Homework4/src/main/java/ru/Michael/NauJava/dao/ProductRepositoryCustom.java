package ru.Michael.NauJava.dao;

import ru.Michael.NauJava.entity.Product;
import java.util.List;

// Интерфейс для кастомных методов ProductRepository

public interface ProductRepositoryCustom {
    List<Product> findByNameContainingAndPriceLessThan(String namePart, double price);
}