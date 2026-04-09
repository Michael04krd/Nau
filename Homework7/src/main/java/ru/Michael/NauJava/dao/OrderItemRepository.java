package ru.Michael.NauJava.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.Michael.NauJava.entity.OrderItem;
import java.util.List;

/**
 * Репозиторий для работы с сущностью {@link OrderItem}.
 * Предоставляет CRUD операции для управления позициями заказов.
 */
@RepositoryRestResource(path = "order-items")
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderId(Long orderId);
    List<OrderItem> findByProductId(Long productId);
}