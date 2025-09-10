package com.AAA.demo.controller;

import com.AAA.demo.dto.UpdateProcductStockDto;
import com.AAA.demo.dto.UpdateProductCostDto;
import com.AAA.demo.dto.UpdateProductPriceDto;
import com.AAA.demo.entities.Product;
import com.AAA.demo.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for managing products.
 */
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    /** Get all products */
    @GetMapping
    public ResponseEntity<List<Product>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    /** Get a product by ID */
    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        Product product = productService.getById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    /** Get a product by name */
    @GetMapping("/search")
    public ResponseEntity<Product> getByName(@RequestParam String name) {
        Product product = productService.getByName(name);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product);
    }

    /** Create a new product */
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        Product created = productService.create(product);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    /** Updates product price by NAME */
    @PutMapping
    public ResponseEntity<Product> updateProductPrice(@Valid @RequestBody UpdateProductPriceDto request) {
        Product updated = productService.updateProductPrice(request.name(), request.price());
        return ResponseEntity.ok(updated);
    }

    /** Updates product cost by NAME */
    @PutMapping
    public ResponseEntity<Product> updateProductCost(@Valid @RequestBody UpdateProductCostDto request) {
        Product updated = productService.updateProductCost(request.name(), request.costPrice());
        return ResponseEntity.ok(updated);
    }

    @PutMapping
    public ResponseEntity<Product> updateProductStock(@Valid @RequestBody UpdateProcductStockDto request) {
        Product updated = productService.updateProductStock(request.name(), request.stock());
        return ResponseEntity.ok(updated);
    }

    /** Delete a product by ID */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
