package ru.Michael.NauJava.dao;

import ru.Michael.NauJava.entity.Order;
import java.util.List;

/**
 * Интерфейс для кастомных методов репозитория {@link OrderRepository}.
 * Реализуется в {@link OrderRepositoryImpl} с использованием Criteria API.
 */
public interface OrderRepositoryCustom {
    List<Order> findCustomerOrdersWithMinAmount(Long customerId, Double minAmount);
}