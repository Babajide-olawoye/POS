package com.AAA.demo.service;

import com.AAA.demo.entities.Product;
import com.AAA.demo.repos.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service layer providing CRUD operations for {@link Product} entities.
 */
@Service
public class ProductService {

    private final ProductRepository productRepository;

    /**
     * Constructs the service with required dependencies.
     *
     * @param productRepository repository for product persistence
     */
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * Retrieves all products.
     */
    public List<Product> getAll() {
        return this.productRepository.findAll();
    }

    /**
     * Looks up a product by id.
     */
    public Product getById(Long id) {
        return this.productRepository.findById(id).orElse(null);
    }

    /**
     * Finds a product by its name.
     */
    public Product getByName(String name) {
        return this.productRepository.findByName(name);
    }

    /**
     * Persists a new product.
     */
    public Product create(Product product) {
        return this.productRepository.save(product);
    }

    /**
     * Deletes a product by id.
     */
    public void delete(Long id) {
        this.productRepository.deleteById(id);
    }
}
