package ru.Michael.NauJava.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.Michael.NauJava.entity.Customer;
import java.util.Optional;

// Репозиторий для работы с сущностью покупатель

public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
}