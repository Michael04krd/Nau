package ru.Michael.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.Michael.NauJava.dao.ProductRepository;
import ru.Michael.NauJava.dao.CategoryRepository;
import ru.Michael.NauJava.entity.Category;
import ru.Michael.NauJava.entity.Product;
import ru.Michael.NauJava.exception.EntityNotFoundException;
import ru.Michael.NauJava.exception.BusinessException;

import java.util.List;

/**
 * Реализация сервиса для работы с товарами.
 * Обеспечивает основную бизнес-логику интернет-магазина:
 * управление товарами, поиск по различным критериям, продажа.
 */

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void addProduct(String name, String categoryName, double price, int quantity) {
        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);

        Category category = categoryRepository.findByName(categoryName)
                .orElseGet(() -> {
                    Category newCategory = new Category();
                    newCategory.setName(categoryName);
                    return categoryRepository.save(newCategory);
                });
        product.setCategory(category);

        productRepository.save(product);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product", id));
    }

    @Override
    public List<Product> findByCategory(String categoryName) {
        return categoryRepository.findByName(categoryName)
                .map(category -> productRepository.findByCategoryId(category.getId()))
                .orElse(List.of());
    }

    @Override
    public List<Product> findCheaperThan(double price) {
        return productRepository.findByPriceLessThan(price);
    }

    @Override
    public void updatePrice(Long id, double price) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product", id));
        product.setPrice(price);
        productRepository.save(product);
    }

    @Override
    public void sellProduct(Long id, int quantity) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product", id));

        if (quantity > product.getQuantity()) {
            System.out.println("=== THROWING BusinessException ===");  // ← добавить
            throw new BusinessException("Недостаточно товара на складе. Доступно: " + product.getQuantity());
        }

        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
        System.out.println("Продали " + quantity + " шт. товара " + product.getName());
    }

    @Override
    public void deleteProduct(Long id) {
        if (!productRepository.existsById(id)) {
            throw new EntityNotFoundException("Product", id);
        }
        productRepository.deleteById(id);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findByNameContainingAndPriceLessThan(String namePart, double price) {
        return productRepository.findByNameContainingAndPriceLessThan(namePart, price);
    }

    @Override
    public List<Product> findByCategoryAndMaxPrice(String categoryName, double maxPrice) {
        return productRepository.findByCategoryAndMaxPrice(categoryName, maxPrice);
    }
}