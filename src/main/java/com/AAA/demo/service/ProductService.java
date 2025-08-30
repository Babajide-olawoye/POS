package com.AAA.demo.service;

import com.AAA.demo.entities.Product;
import com.AAA.demo.repos.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public ProductService() {
    }

    public List<Product> getAll() {
        return this.productRepository.findAll();
    }

    public Product getById(Long id) {
        return this.productRepository.findById(id);
    }
    public Product getByName(String name) {
        return this.productRepository.findByName(name);
    }

    public Product create(Product product) {
        return (Product)this.productRepository.save(product);
    }

    public void delete(Long id) {
        this.productRepository.deleteById(id);
    }
}
