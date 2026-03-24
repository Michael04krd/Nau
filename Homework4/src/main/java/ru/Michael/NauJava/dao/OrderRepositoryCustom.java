package ru.Michael.NauJava.dao;

import ru.Michael.NauJava.entity.Order;
import java.util.List;

// Интерфейс для кастомных методов OrderRepository

public interface OrderRepositoryCustom {
    List<Order> findCustomerOrdersWithMinAmount(Long customerId, Double minAmount);
}