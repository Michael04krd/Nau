package ru.Michael.NauJava.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.Michael.NauJava.entity.Customer;
import java.util.Optional;

/**
 * Репозиторий для работы с сущностью {@link Customer}.
 * Предоставляет CRUD операции для управления покупателями.
 */
@RepositoryRestResource(path = "customers")
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
}