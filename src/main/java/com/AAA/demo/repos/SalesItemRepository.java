package com.AAA.demo.repos;

import com.AAA.demo.entities.SaleItem;
import org.springframework.data.repository.ListPagingAndSortingRepository;


public interface SalesItemRepository extends ListPagingAndSortingRepository<SaleItem, Long> {

}
