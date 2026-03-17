package ru.Michael.NauJava.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.Michael.NauJava.entity.OrderItem;
import java.util.List;

// Репозиторий для работы с сущностью заказ товара

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderId(Long orderId);
    List<OrderItem> findByProductId(Long productId);
}