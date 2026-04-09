package ru.Michael.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.Michael.NauJava.dao.CustomerRepository;
import ru.Michael.NauJava.dao.RoleRepository;
import ru.Michael.NauJava.entity.Customer;
import ru.Michael.NauJava.entity.Role;

import java.util.Optional;
import java.util.Set;

/**
 * Реализация сервиса для работы с покупателями.
 */

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @Override
    public Customer register(Customer customer) {
        // Проверяем, не существует ли пользователь с таким email
        if (customerRepository.findByEmail(customer.getEmail()).isPresent()) {
            throw new RuntimeException("Пользователь с email " + customer.getEmail() + " уже существует");
        }
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.setEnabled(true);

        Role userRole = roleRepository.findByName("USER")
                .orElseThrow(() -> new RuntimeException("Роль USER не найдена в БД"));
        customer.setRoles(Set.of(userRole));

        return customerRepository.save(customer);
    }

    @Override
    public Optional<Customer> findByEmail(String email) {
        return customerRepository.findByEmail(email);
    }
}