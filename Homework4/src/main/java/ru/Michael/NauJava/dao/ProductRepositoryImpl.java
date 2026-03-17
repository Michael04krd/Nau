package ru.Michael.NauJava.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;
import ru.Michael.NauJava.entity.Product;

import java.util.List;

// Реализация кастомных методов

@Repository
public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Product> findByNameContainingAndPriceLessThan(String namePart, double price) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> product = query.from(Product.class);

        Predicate namePredicate = cb.like(cb.lower(product.get("name")),
                "%" + namePart.toLowerCase() + "%");
        Predicate pricePredicate = cb.lessThan(product.get("price"), price);

        query.select(product).where(cb.and(namePredicate, pricePredicate));

        return entityManager.createQuery(query).getResultList();
    }
}