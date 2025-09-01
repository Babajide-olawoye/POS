package com.AAA.demo.repos;

import com.AAA.demo.entities.SaleItem;
import org.springframework.data.repository.ListPagingAndSortingRepository;

import java.util.Optional;

public interface SalesItemRepository extends ListPagingAndSortingRepository<SaleItem, Long> {
    Optional<SaleItem> findById(Long id);

}
