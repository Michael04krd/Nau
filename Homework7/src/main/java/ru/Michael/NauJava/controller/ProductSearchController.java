package ru.Michael.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.Michael.NauJava.entity.Product;
import ru.Michael.NauJava.service.ProductService;

import java.util.List;

/**
 * REST-контроллер для работы с товарами.
 * Предоставляет API для поиска товаров по различным критериям,
 * а также для получения товара по ID и оформления продажи.
 * Базовый путь: {@code /products}
 */

@RestController
@RequestMapping("/products")
public class ProductSearchController {

    private final ProductService productService;

    @Autowired
    public ProductSearchController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/search/by-name-price")
    public List<Product> searchByNameAndPrice(
            @RequestParam String name,
            @RequestParam double maxPrice) {
        return productService.findByNameContainingAndPriceLessThan(name, maxPrice);
    }

    @GetMapping("/search/by-category-price")
    public List<Product> searchByCategoryAndPrice(
            @RequestParam String category,
            @RequestParam double maxPrice) {
        return productService.findByCategoryAndMaxPrice(category, maxPrice);
    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.findById(id);
    }

    @PostMapping("/{id}/sell")
    public String sellProduct(@PathVariable Long id, @RequestParam int quantity) {
        productService.sellProduct(id, quantity);
        return "Товар успешно продан";
    }
}