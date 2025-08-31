package com.AAA.demo.service;

import com.AAA.demo.repos.SalesItemRepository;
import org.springframework.stereotype.Service;

@Service
public class SaleItemService {
    private final SalesItemRepository salesItemRepository;

    /**
     * Constructs the service with required dependencies.
     *
     * @param salesItemRepository repository for product persistence
     */
    public SaleItemService(SalesItemRepository salesItemRepository) {
        this.salesItemRepository = salesItemRepository;
    }
}
