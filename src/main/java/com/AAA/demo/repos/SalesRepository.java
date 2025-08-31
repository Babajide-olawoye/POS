package com.AAA.demo.repos;

import com.AAA.demo.entities.Sales;
import org.springframework.data.repository.ListCrudRepository;

public interface SalesRepository extends ListCrudRepository<Sales, Long> {
}
