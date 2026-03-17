package ru.Michael.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.Michael.NauJava.dao.ProductRepository;
import ru.Michael.NauJava.dao.CategoryRepository;
import ru.Michael.NauJava.entity.Category;
import ru.Michael.NauJava.entity.Product;
import java.util.List;
import java.util.Optional;

// Реализация сервиса товаров. Работает с категориями и выполняет основные операции

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
    public void addProduct(Long id, String name, String categoryName, double price, int quantity) {
        Product product = new Product();
        product.setId(id);  // JPA сам сгенерит ID
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);

        // Находим или создаем категорию
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
        return productRepository.findById(id).orElse(null);
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
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            product.setPrice(price);
            productRepository.save(product);
        }
    }

    @Override
    public void sellProduct(Long id, int quantity) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isEmpty()) {
            System.out.println("Продукт не найден!");
            return;
        }

        Product product = optionalProduct.get();
        if (quantity > product.getQuantity()) {
            System.out.println("Заявленное количество (" + quantity + ") больше допустимого (" + product.getQuantity() + ")");
            return;
        }

        product.setQuantity(product.getQuantity() - quantity);
        productRepository.save(product);
        System.out.println("Продали " + quantity + " шт. товара " + product.getName());
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}