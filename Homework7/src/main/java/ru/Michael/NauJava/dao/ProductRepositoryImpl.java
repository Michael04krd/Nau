package ru.Michael.NauJava.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.stereotype.Repository;
import ru.Michael.NauJava.entity.Category;
import ru.Michael.NauJava.entity.Product;

import java.util.List;

/**
 * Реализация кастомных методов {@link ProductRepositoryCustom}
 * с использованием Criteria API.
 */
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

    @Override
    public List<Product> findByCategoryAndMaxPrice(String categoryName, double maxPrice) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> query = cb.createQuery(Product.class);
        Root<Product> product = query.from(Product.class);
        Join<Product, Category> category = product.join("category");

        Predicate categoryPredicate = cb.equal(category.get("name"), categoryName);
        Predicate pricePredicate = cb.lessThan(product.get("price"), maxPrice);

        query.select(product).where(cb.and(categoryPredicate, pricePredicate));

        return entityManager.createQuery(query).getResultList();
    }
}