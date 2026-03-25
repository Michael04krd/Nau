package ru.Michael.NauJava.dao;

import ru.Michael.NauJava.entity.Product;
import java.util.List;

/**
 * Интерфейс для кастомных методов репозитория {@link ProductRepository}.
 * Реализуется в {@link ProductRepositoryImpl} с использованием Criteria API.
 */
public interface ProductRepositoryCustom {
    List<Product> findByNameContainingAndPriceLessThan(String namePart, double price);

    List<Product> findByCategoryAndMaxPrice(String categoryName, double maxPrice);
}