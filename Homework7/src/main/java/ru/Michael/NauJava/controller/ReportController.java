package ru.Michael.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.Michael.NauJava.entity.Report;
import ru.Michael.NauJava.service.ReportService;

import java.util.HashMap;
import java.util.Map;

/**
 * REST-контроллер для работы с отчетами.
 * Предоставляет API для асинхронного формирования отчетов.
 * Базовый путь: {@code /reports}
 */

@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    @Autowired
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @PostMapping
    public ResponseEntity<Map<String, Object>> createReport() {
        Long reportId = reportService.createReport();
        reportService.generateReportAsync(reportId);

        Map<String, Object> response = new HashMap<>();
        response.put("reportId", reportId);
        response.put("status", "CREATED");
        response.put("message", "Отчет начал формироваться. Используйте /reports/" + reportId + " для получения результата");

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReport(@PathVariable Long id) {
        Report report = reportService.getReport(id);

        switch (report.getStatus()) {
            case COMPLETED:
                // Отчет готов
                return ResponseEntity.ok()
                        .header("Content-Type", "text/html; charset=UTF-8")
                        .body(report.getContent());

            case ERROR:
                // Ошибка
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("reportId", report.getId());
                errorResponse.put("status", report.getStatus());
                errorResponse.put("errorMessage", report.getErrorMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);

            case CREATED:
            case IN_PROGRESS:
            default:
                // Отчет формируется
                Map<String, Object> progressResponse = new HashMap<>();
                progressResponse.put("reportId", report.getId());
                progressResponse.put("status", report.getStatus());
                progressResponse.put("message", "Отчет еще формируется. Попробуйте позже.");
                return ResponseEntity.status(HttpStatus.ACCEPTED).body(progressResponse);
        }
    }
}