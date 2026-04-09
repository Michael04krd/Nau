package ru.Michael.NauJava.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.Michael.NauJava.entity.Role;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}