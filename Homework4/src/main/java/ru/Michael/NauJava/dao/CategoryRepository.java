package ru.Michael.NauJava.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.Michael.NauJava.entity.Category;
import java.util.Optional;

// Репозиторий для работы с сущностью категория

public interface CategoryRepository extends JpaRepository<Category, Long> {
    Optional<Category> findByName(String name);
}