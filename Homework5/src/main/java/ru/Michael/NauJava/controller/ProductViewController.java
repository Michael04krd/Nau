package ru.Michael.NauJava.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.Michael.NauJava.entity.Product;
import ru.Michael.NauJava.service.ProductService;

import java.util.List;

/**
 * Контроллер для отображения HTML-страниц.
 * Использует Thymeleaf для генерации представлений.
 * Базовый путь: {@code /products-view}
 */

@Controller
@RequestMapping("/products-view")
public class ProductViewController {

    private final ProductService productService;

    @Autowired
    public ProductViewController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/list")
    public String listProducts(Model model) {
        List<Product> products = productService.findAll();
        model.addAttribute("products", products);
        return "product-list";
    }
}