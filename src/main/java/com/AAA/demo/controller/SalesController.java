package com.AAA.demo.controller;

import com.AAA.demo.dto.CreateSaleDto;
import com.AAA.demo.dto.CreateSaleItemDto;
import com.AAA.demo.dto.SalesViewDto;
import com.AAA.demo.service.SalesService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sales")
@Validated
public class SalesController {
    private final SalesService salesService;

    public SalesController(SalesService salesService) {
        this.salesService = salesService;
    }

    @PostMapping
    public ResponseEntity<SalesViewDto> create(@RequestBody @Valid CreateSaleDto dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(salesService.createSale(dto));
    }

    @PostMapping("/{saleId}/items")
    public ResponseEntity<SalesViewDto> addItem(@PathVariable Long saleId, @RequestBody @Valid CreateSaleItemDto dto) {
        return ResponseEntity.ok(salesService.addItem(saleId, dto));
    }

    @DeleteMapping("/{saleId}/items/{itemId}")
    public ResponseEntity<SalesViewDto> deleteItem(@PathVariable Long saleId, @PathVariable Long itemId) {
        return ResponseEntity.ok(salesService.deleteItem(saleId, itemId));
    }
}
