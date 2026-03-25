package ru.Michael.NauJava.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.Michael.NauJava.entity.Category;
import java.util.Optional;

/**
 * Репозиторий для работы с сущностью {@link Category}.
 * Предоставляет CRUD операции для управления категориями товаров.
 */
@RepositoryRestResource(path = "categories")
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}