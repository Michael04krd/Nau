package ru.Michael.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.Michael.NauJava.dao.CustomerRepository;
import ru.Michael.NauJava.dao.ProductRepository;
import ru.Michael.NauJava.dao.ReportRepository;
import ru.Michael.NauJava.entity.Product;
import ru.Michael.NauJava.entity.Report;
import ru.Michael.NauJava.exception.EntityNotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * Реализация сервиса для работы с отчетами.
 */

@Service
public class ReportServiceImpl implements ReportService {

    private final ReportRepository reportRepository;
    private final ProductRepository productRepository;
    private final CustomerRepository customerRepository;

    @Autowired
    public ReportServiceImpl(ReportRepository reportRepository, ProductRepository productRepository, CustomerRepository customerRepository) {
        this.reportRepository = reportRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public Long createReport() {
        Report report = new Report();
        Report savedReport = reportRepository.save(report);
        return savedReport.getId();
    }

    @Override
    public void generateReportAsync(Long reportId) {
        CompletableFuture.runAsync(() -> {
            try {
                Report report = getReport(reportId);
                report.setStatus(Report.Status.IN_PROGRESS);
                reportRepository.save(report);

                long startTotal = System.nanoTime();
                final Long[] usersCount = new Long[1];
                final List<Product>[] productsList = new List[1];
                final long[] usersTime = new long[1];
                final long[] productsTime = new long[1];

                // Поток 1 - пользователи
                Thread userThread = new Thread(() -> {
                    long start = System.nanoTime();
                    usersCount[0] = customerRepository.count();
                    usersTime[0] = System.nanoTime() - start;
                });

                // Поток 2 - товары
                Thread productThread = new Thread(() -> {
                    long start = System.nanoTime();
                    productsList[0] = productRepository.findAll();
                    productsTime[0] = System.nanoTime() - start;
                });

                userThread.start();
                productThread.start();

                userThread.join();
                productThread.join();

                long totalTime = System.nanoTime() - startTotal;

                String htmlContent = generateReportHtml(usersCount[0], productsList[0], usersTime[0], productsTime[0], totalTime);

                report.setContent(htmlContent);
                report.setStatus(Report.Status.COMPLETED);
                report.setCompletedAt(LocalDateTime.now());
                reportRepository.save(report);

            } catch (Exception e) {
                Report report = getReport(reportId);
                report.setStatus(Report.Status.ERROR);
                report.setErrorMessage(e.getMessage());
                reportRepository.save(report);
            }
        });
    }

    @Override
    public Report getReport(Long reportId) {
        return reportRepository.findById(reportId)
                .orElseThrow(() -> new EntityNotFoundException("Report", reportId));
    }

    /**
     * Генерирует HTML-таблицу со списком всех товаров на складе
     * @return HTML-строка с отчетом
     */

    private String generateReportHtml(long usersCount, List<Product> products,
                                      long usersTimeNanos, long productsTimeNanos,
                                      long totalTimeNanos) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n");
        html.append("<html>\n");
        html.append("<head>\n");
        html.append("    <meta charset=\"UTF-8\">\n");
        html.append("    <title>Статистика приложения</title>\n");
        html.append("    <style>\n");
        html.append("        body { font-family: Arial, sans-serif; margin: 20px; }\n");
        html.append("        table { border-collapse: collapse; width: 100%; margin-top: 20px; }\n");
        html.append("        th, td { border: 1px solid #ddd; padding: 8px; text-align: left; }\n");
        html.append("        th { background-color: #f2f2f2; }\n");
        html.append("        .stat { background-color: #e8f4f8; padding: 10px; margin: 10px 0; }\n");
        html.append("        .stat-item { margin: 5px 0; }\n");
        html.append("    </style>\n");
        html.append("</head>\n");
        html.append("<body>\n");

        html.append("    <h1>Статистика приложения</h1>\n");
        html.append("    <p>Дата формирования: ").append(LocalDateTime.now()).append("</p>\n");

        html.append("    <div class=\"stat\">\n");
        html.append("        <h3>Статистика</h3>\n");
        html.append("        <div class=\"stat-item\">Зарегистрировано пользователей: <strong>").append(usersCount).append("</strong></div>\n");
        html.append("        <div class=\"stat-item\">Время подсчета пользователей: <strong>").append(formatNanoTime(usersTimeNanos)).append("</strong></div>\n");
        html.append("        <div class=\"stat-item\">Всего товаров на складе: <strong>").append(products.size()).append("</strong></div>\n");
        html.append("        <div class=\"stat-item\">Время получения списка товаров: <strong>").append(formatNanoTime(productsTimeNanos)).append("</strong></div>\n");
        html.append("        <div class=\"stat-item\">Общее время формирования отчета: <strong>").append(formatNanoTime(totalTimeNanos)).append("</strong></div>\n");
        html.append("    </div>\n");

        html.append("    <h3>Список товаров на складе</h3>\n");
        html.append("    <table>\n");
        html.append("        <thead>\n");
        html.append("            <tr>\n");
        html.append("                <th>ID</th>\n");
        html.append("                <th>Название</th>\n");
        html.append("                <th>Категория</th>\n");
        html.append("                <th>Цена (₽)</th>\n");
        html.append("                <th>Количество</th>\n");
        html.append("            </tr>\n");
        html.append("        </thead>\n");
        html.append("        <tbody>\n");

        for (Product product : products) {
            html.append("            <tr>\n");
            html.append("                <td>").append(product.getId()).append("</td>\n");
            html.append("                <td>").append(escapeHtml(product.getName())).append("</td>\n");
            html.append("                <td>").append(product.getCategory() != null ? escapeHtml(product.getCategory().getName()) : "Без категории").append("</td>\n");
            html.append("                <td>").append(String.format("%.2f", product.getPrice())).append("</td>\n");
            html.append("                <td>").append(product.getQuantity()).append("</td>\n");
            html.append("            </tr>\n");
        }

        html.append("        </tbody>\n");
        html.append("    </table>\n");
        html.append("</body>\n");
        html.append("</html>\n");

        return html.toString();
    }

    private String formatNanoTime(long nanos) {
        if (nanos < 1000) {
            return nanos + " нс";
        } else if (nanos < 1_000_000) {
            return String.format("%.2f мкс", nanos / 1000.0);
        } else if (nanos < 1_000_000_000) {
            return String.format("%.2f мс", nanos / 1_000_000.0);
        } else {
            return String.format("%.2f с", nanos / 1_000_000_000.0);
        }
    }

    private String escapeHtml(String text) {
        if (text == null) return "";
        return text
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}