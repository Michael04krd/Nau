package ru.Michael.NauJava.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.Michael.NauJava.entity.Category;
import ru.Michael.NauJava.entity.Product;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Тестируются как стандартные так и кастомные методы

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testFindByNameContainingAndPriceLessThan() {
        Category category = new Category();
        category.setName("Электроника");
        categoryRepository.save(category);

        Product product1 = new Product();
        product1.setName("Ноутбук HP");
        product1.setPrice(50000);
        product1.setQuantity(10);
        product1.setCategory(category);
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setName("Ноутбук Lenovo");
        product2.setPrice(45000);
        product2.setQuantity(5);
        product2.setCategory(category);
        productRepository.save(product2);

        Product product3 = new Product();
        product3.setName("Мышь Logitech");
        product3.setPrice(2000);
        product3.setQuantity(100);
        product3.setCategory(category);
        productRepository.save(product3);

        List<Product> result = productRepository.findByNameContainingAndPriceLessThan("ноут", 48000);

        assertEquals(1, result.size());
        assertEquals("Ноутбук Lenovo", result.get(0).getName());
    }
}