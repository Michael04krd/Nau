package ru.Michael.NauJava.service;

import ru.Michael.NauJava.entity.Report;

/**
 * Сервис для работы с отчетами.
 * Обеспечивает создание, асинхронную генерацию и получение отчетов.
 */

public interface ReportService {
    Long createReport();
    void generateReportAsync(Long reportId);
    Report getReport(Long reportId);
}