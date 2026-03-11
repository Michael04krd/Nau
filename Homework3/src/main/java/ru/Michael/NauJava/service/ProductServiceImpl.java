package ru.Michael.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.Michael.NauJava.dao.ProductRepository;
import ru.Michael.NauJava.entity.Product;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void addProduct(Long id, String name, String category, double price, int quantity) {
        Product product = new Product();
        product.setId(id);
        product.setName(name);
        product.setCategory(category);
        product.setPrice(price);
        product.setQuantity(quantity);
        productRepository.create(product);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.read(id);
    }

    @Override
    public List<Product> findByCategory(String category) {
        return productRepository.readAll().stream()
                .filter(p -> p.getCategory().equals(category))
                .collect(Collectors.toList());
    }

    @Override
    public List<Product> findCheaperThan(double price) {
        return productRepository.readAll().stream()
                .filter(p -> p.getPrice() < price)
                .collect(Collectors.toList());
    }

    @Override
    public void updatePrice(Long id, double price) {
        Product product = productRepository.read(id);
        if (product != null) {
            product.setPrice(price);
            productRepository.update(product);
        }
    }

    @Override
    public void sellProduct(Long id, int quantity) {
        Product product = productRepository.read(id);
        if (product == null) {
            System.out.println("Продукт не найден!");
            return;
        }
        if (quantity > product.getQuantity()) {
            System.out.println("Заявленное количество больше допустимого!");
            return;
        }
        product.setQuantity(product.getQuantity() - quantity);
        productRepository.update(product);
        System.out.println("Продали " + quantity + "шт. товара " + product.getName());
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.delete(id);
    }
}