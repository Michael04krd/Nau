package ru.Michael.NauJava.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.Michael.NauJava.entity.Product;
import java.util.List;

// Репозиторий для работы с сущностью товар

public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {

    List<Product> findByName(String name);

    List<Product> findByPriceLessThan(double price);

    List<Product> findByCategoryId(Long categoryId);

    // Query метод

    @Query("SELECT p FROM Product p WHERE p.category.name = :categoryName AND p.price < :maxPrice")
    List<Product> findByCategoryAndMaxPrice(@Param("categoryName") String categoryName,
                                            @Param("maxPrice") double maxPrice);

    List<Product> findByNameContainingAndPriceLessThan(String namePart, double price);
}