package com.example.demo.service;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repository;
    private final Counter productCreations;
    private final Counter productDeletions;

    public ProductService(ProductRepository repository, MeterRegistry registry) {
        this.repository = repository;
        this.productCreations = Counter.builder("product.operations")
                .tag("type", "creation")
                .description("Number of product creations")
                .register(registry);
        this.productDeletions = Counter.builder("product.operations")
                .tag("type", "deletion")
                .description("Number of product deletions")
                .register(registry);
    }

    public Product createProduct(Product product) {
        Product saved = repository.save(product);
        productCreations.increment();
        return saved;
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Product getProduct(Long id) {
        return repository.findById(id);
    }

    public Product updateProduct(Long id, Product product) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Product not found with id: " + id);
        }
        product.setId(id);
        return repository.save(product);
    }

    public void deleteProduct(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            productDeletions.increment();
        } else {
            throw new RuntimeException("Product not found with id: " + id);
        }
    }
}
