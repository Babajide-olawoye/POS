package com.AAA.demo.repos;

import com.AAA.demo.entities.Product;
import org.springframework.data.repository.ListCrudRepository;

/**
 * Repository for {@link Product} entities.
 * <p>
 * Extends {@link ListCrudRepository} to provide basic CRUD operations and
 * exposes a finder for looking up products by name.
 */
public interface ProductRepository extends ListCrudRepository<Product, Long> {

    /**
     * Retrieves a product by its name.
     *
     * @param name product name
     * @return matching product or {@code null} if none found
     */
    Product findByName(String name);
}
