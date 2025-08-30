package com.AAA.demo.repos;

import com.AAA.demo.entities.Product;
import org.springframework.data.repository.ListPagingAndSortingRepository;

import java.util.List;

public interface ProductRepository extends ListPagingAndSortingRepository<Product, Integer> {

    List<Product> findAll();

    Product findById(Long id);
    Product findByName(String name);

    Object save(Product product);

    void deleteById(Long id);
}
