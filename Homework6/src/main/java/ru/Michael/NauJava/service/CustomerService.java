package ru.Michael.NauJava.service;

import ru.Michael.NauJava.entity.Customer;

import java.util.Optional;

/**
 * Сервис для работы с пользователями (покупателями).
 * Содержит бизнес-логику регистрации и поиска пользователей.
 */

public interface CustomerService {
    Customer register(Customer customer);

    Optional<Customer> findByEmail(String email);
}
