package ru.Michael.NauJava.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.Michael.NauJava.dao.CustomerRepository;
import ru.Michael.NauJava.dao.ProductRepository;
import ru.Michael.NauJava.entity.Category;
import ru.Michael.NauJava.entity.Customer;
import ru.Michael.NauJava.entity.Product;
import ru.Michael.NauJava.service.OrderService.OrderItemRequest;
import ru.Michael.NauJava.dao.CategoryRepository;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Интеграционные тесты. Проверяем транзакционную логику создания заказа

@SpringBootTest
@Transactional
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    void testCreateOrderSuccess() {
        Customer customer = new Customer();
        customer.setName("Тестовый Клиент");
        customer.setEmail("test@mail.ru");
        customerRepository.save(customer);

        Category category = new Category();
        category.setName("Тестовая категория");
        categoryRepository.save(category);

        Product product1 = new Product();
        product1.setName("Телефон");
        product1.setPrice(20000.0);
        product1.setQuantity(10);
        product1.setCategory(category);
        productRepository.save(product1);

        Product product2 = new Product();
        product2.setName("Чехол");
        product2.setPrice(1000.0);
        product2.setQuantity(50);
        product2.setCategory(category);
        productRepository.save(product2);

        List<OrderItemRequest> items = List.of(
                new OrderItemRequest(product1.getId(), 2),
                new OrderItemRequest(product2.getId(), 3)
        );

        var order = orderService.createOrder(customer.getId(), items);

        assertNotNull(order.getId());
        assertEquals(2, order.getItems().size());
        assertEquals(43000.0, order.getTotalAmount());

        // Проверяем, что количество на складе уменьшилось
        Product updatedProduct1 = productRepository.findById(product1.getId()).orElseThrow();
        assertEquals(8, updatedProduct1.getQuantity());

        Product updatedProduct2 = productRepository.findById(product2.getId()).orElseThrow();
        assertEquals(47, updatedProduct2.getQuantity());
    }

    @Test
    void testCreateOrderFail_NotEnoughStock() {
        Customer customer = new Customer();
        customer.setName("Тестовый Клиент");
        customer.setEmail("test2@mail.ru");
        customerRepository.save(customer);

        Category category = new Category();
        category.setName("Тестовая категория");
        categoryRepository.save(category);

        Product product = new Product();
        product.setName("Редкий товар");
        product.setPrice(100000.0);
        product.setQuantity(1);
        product.setCategory(category);
        productRepository.save(product);

        List<OrderItemRequest> items = List.of(
                new OrderItemRequest(product.getId(), 2)
        );

        assertThrows(RuntimeException.class, () -> {
            orderService.createOrder(customer.getId(), items);
        });

        Product unchangedProduct = productRepository.findById(product.getId()).orElseThrow();
        assertEquals(1, unchangedProduct.getQuantity());
    }
}