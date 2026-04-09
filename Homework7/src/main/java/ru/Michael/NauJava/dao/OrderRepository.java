package ru.Michael.NauJava.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.Michael.NauJava.entity.Order;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Репозиторий для работы с сущностью {@link Order}.
 * Предоставляет CRUD операции для управления заказами,
 * а также кастомные методы через {@link OrderRepositoryCustom}.
 */
@RepositoryRestResource(path = "orders")
public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {
    List<Order> findByCustomerId(Long customerId);
    List<Order> findByOrderDateBetween(LocalDateTime start, LocalDateTime end);
}