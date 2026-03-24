package ru.Michael.NauJava.service;

import ru.Michael.NauJava.entity.Order;
import java.util.List;

public interface OrderService {
    // Создает новый заказ для покупателя тракзационно
    Order createOrder(Long customerId, List<OrderItemRequest> items);

    // Используется для передачи данных о позиции заказа
    record OrderItemRequest(Long productId, int quantity) {}
}