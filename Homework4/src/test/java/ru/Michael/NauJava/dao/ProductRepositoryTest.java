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
    @Test
    void testFindByCategoryAndMaxPrice() {
        Category electronics = new Category();
        electronics.setName("Электроника");
        categoryRepository.save(electronics);

        Category books = new Category();
        books.setName("Книги");
        categoryRepository.save(books);

        Product product1 = new Product();
        product1.setName("Ноутбук HP");
        product1.setPrice(50000);
        product1.setQuantity(10);
        product1.setCategory(electronics);
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setName("Мышь Logitech");
        product2.setPrice(2000);
        product2.setQuantity(100);
        product2.setCategory(electronics);
        productRepository.save(product2);

        Product product3 = new Product();
        product3.setName("Война и мир");
        product3.setPrice(800);
        product3.setQuantity(50);
        product3.setCategory(books);
        productRepository.save(product3);

        Product product4 = new Product();
        product4.setName("Java. Полное руководство");
        product4.setPrice(3000);
        product4.setQuantity(20);
        product4.setCategory(books);
        productRepository.save(product4);

        List<Product> result = productRepository.findByCategoryAndMaxPrice("Электроника", 30000);

        assertEquals(1, result.size());
        assertEquals("Мышь Logitech", result.get(0).getName());
        assertEquals(2000, result.get(0).getPrice());

        List<Product> resultBooks = productRepository.findByCategoryAndMaxPrice("Книги", 1000);

        assertEquals(1, resultBooks.size());
        assertEquals("Война и мир", resultBooks.get(0).getName());

        List<Product> resultEmpty = productRepository.findByCategoryAndMaxPrice("Мебель", 5000);

        assertEquals(0, resultEmpty.size());
    }
}