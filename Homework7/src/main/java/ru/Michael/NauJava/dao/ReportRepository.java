package ru.Michael.NauJava.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.Michael.NauJava.entity.Report;

/**
 * Репозиторий для работы с сущностью {@link Report}.
 * Предоставляет CRUD операции для управления отчетами.
 */

@RepositoryRestResource(path = "reports")
public interface ReportRepository extends JpaRepository<Report, Long> {
}