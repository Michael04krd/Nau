package ru.Michael.NauJava.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.Michael.NauJava.entity.Order;
import java.time.LocalDateTime;
import java.util.List;

// Репозиторий для работы с сущностью заказ

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {
    List<Order> findByCustomerId(Long customerId);
    List<Order> findByOrderDateBetween(LocalDateTime start, LocalDateTime end);
}