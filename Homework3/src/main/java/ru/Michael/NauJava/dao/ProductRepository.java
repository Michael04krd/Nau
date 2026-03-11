package ru.Michael.NauJava.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.Michael.NauJava.entity.Product;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductRepository implements CrudRepository<Product, Long> {
    private final List<Product> productContainer;

    @Autowired
    public ProductRepository(List<Product> productContainer) {
        this.productContainer = productContainer;
    }

    @Override
    public void create(Product product) {
        productContainer.add(product);
    }

    @Override
    public Product read(Long id) {
        return productContainer.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Product> readAll() {
        return new ArrayList<>(productContainer); // возвращаем копию!
    }

    @Override
    public void update(Product product) {
        Product exitingProduct = read(product.getId());
        if (exitingProduct != null) {
            exitingProduct.setName(product.getName());
            exitingProduct.setCategory(product.getCategory());
            exitingProduct.setPrice(product.getPrice());
            exitingProduct.setQuantity(product.getQuantity());
        }
    }

    @Override
    public void delete(Long id) {
        productContainer.removeIf(p -> p.getId().equals(id));
    }
}
