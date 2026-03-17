package ru.Michael.NauJava.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import ru.Michael.NauJava.entity.Customer;
import ru.Michael.NauJava.entity.Order;

import java.util.List;

// Реализация кастомных методов

@Repository
public class OrderRepositoryImpl implements OrderRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    // Делаем JOIN программно
    @Override
    public List<Order> findCustomerOrdersWithMinAmount(Long customerId, Double minAmount) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Order> query = cb.createQuery(Order.class);
        Root<Order> order = query.from(Order.class);
        Join<Order, Customer> customer = order.join("customer");

        Predicate customerPredicate = cb.equal(customer.get("id"), customerId);
        Predicate amountPredicate = cb.greaterThan(order.get("totalAmount"), minAmount);

        query.select(order).where(cb.and(customerPredicate, amountPredicate));

        return entityManager.createQuery(query).getResultList();
    }
}