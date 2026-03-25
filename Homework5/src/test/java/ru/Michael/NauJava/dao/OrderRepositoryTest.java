package ru.Michael.NauJava.dao;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import ru.Michael.NauJava.entity.*;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

// Тесты для кастомных методов

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void testFindCustomerOrdersWithMinAmount() {
        Customer customer = new Customer();
        customer.setName("Иван Петров");
        customer.setEmail("ivan@mail.ru");
        customerRepository.save(customer);

        Order order1 = new Order();
        order1.setCustomer(customer);
        order1.setOrderDate(LocalDateTime.now());
        order1.setTotalAmount(5000.0);
        orderRepository.save(order1);

        Order order2 = new Order();
        order2.setCustomer(customer);
        order2.setOrderDate(LocalDateTime.now());
        order2.setTotalAmount(15000.0);
        orderRepository.save(order2);

        Order order3 = new Order();
        order3.setCustomer(customer);
        order3.setOrderDate(LocalDateTime.now());
        order3.setTotalAmount(8000.0);
        orderRepository.save(order3);

        List<Order> result = orderRepository.findCustomerOrdersWithMinAmount(customer.getId(), 10000.0);

        assertEquals(1, result.size());
        assertEquals(15000.0, result.get(0).getTotalAmount());
    }
}