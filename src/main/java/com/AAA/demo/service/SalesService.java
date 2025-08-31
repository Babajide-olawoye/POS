package com.AAA.demo.service;

import com.AAA.demo.dto.CreateSaleDto;
import com.AAA.demo.dto.CreateSaleItem;
import com.AAA.demo.dto.SaleItemsViewDto;
import com.AAA.demo.dto.SalesViewDto;
import com.AAA.demo.entities.Product;
import com.AAA.demo.entities.Sales;
import com.AAA.demo.entities.SaleItem;
import com.AAA.demo.repos.ProductRepository;
import com.AAA.demo.repos.SalesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class SalesService {
    private final SalesRepository salesRepository;
    private final ProductRepository productRepo;

    public SalesService(SalesRepository salesRepository, ProductRepository productRepo) {
        this.salesRepository = salesRepository;
        this.productRepo = productRepo;
    }

    // ----- CREATE SALE (with items) -----
    @Transactional
    public SalesViewDto createSale(CreateSaleDto dto) {
        if (dto.items() == null || dto.items().isEmpty()) {
            throw new IllegalArgumentException("Sale must contain at least one item");
        }

        List<SaleItem> items = new ArrayList<>();
        BigDecimal subtotal = BigDecimal.ZERO;

        for (CreateSaleItem it : dto.items()) {
            Product p = productRepo.findById(it.productId())
                    .orElseThrow(() -> new IllegalArgumentException("Product %d not found".formatted(it.productId())));

            if (it.quantity() == null || it.quantity() <= 0) {
                throw new IllegalArgumentException("Invalid quantity for product " + it.productId());
            }

            // ----- PRICE RULE -----
            BigDecimal unitPrice = (p.getPrice().compareTo(BigDecimal.ZERO) > 0)
                    ? p.getPrice()
                    : (it.unitPrice() != null ? it.unitPrice() : BigDecimal.ZERO);

            BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(it.quantity()));

            SaleItem si = new SaleItem();
            si.setProductId(p.getId());
            si.setQuantity(it.quantity());
            si.setUnitPrice(unitPrice);
            si.setSubtotal(lineTotal);
            items.add(si);

            subtotal = subtotal.add(lineTotal);
        }

        Sales sale = new Sales();
        sale.setItems(items);
        sale.setSubtotal(subtotal);
        sale.setDiscount(nvl(dto.discount()));
        sale.setTaxRate(nvl(dto.taxRate()));
        sale.setPaymentMethod(dto.paymentMethod());
        sale.setCreatedAt(Instant.now());
        recalcTotals(sale);

        Sales saved = salesRepository.save(sale);
        return toView(saved);
    }

    // ----- ADD ONE ITEM TO EXISTING SALE -----
    @Transactional
    public SalesViewDto addItem(Long saleId, CreateSaleItem dto) {
        Sales sale = salesRepository.findById(saleId)
                .orElseThrow(() -> new IllegalArgumentException("Sale %d not found".formatted(saleId)));

        Product p = productRepo.findById(dto.productId())
                .orElseThrow(() -> new IllegalArgumentException("Product %d not found".formatted(dto.productId())));

        if (dto.quantity() == null || dto.quantity() <= 0) {
            throw new IllegalArgumentException("Invalid quantity");
        }

        // price rule
        BigDecimal unitPrice = (p.getPrice().compareTo(BigDecimal.ZERO) > 0)
                ? p.getPrice()
                : (dto.unitPrice() != null ? dto.unitPrice() : BigDecimal.ZERO);

        BigDecimal lineTotal = unitPrice.multiply(BigDecimal.valueOf(dto.quantity()));

        SaleItem si = new SaleItem();
        si.setProductId(p.getId());
        si.setQuantity(dto.quantity());
        si.setUnitPrice(unitPrice);
        si.setSubtotal(lineTotal);

        sale.getItems().add(si);
        // update totals
        recalcTotals(sale);

        Sales saved = salesRepository.save(sale); // collection is authoritative; insert new row
        return toView(saved);
    }

    // ----- DELETE ONE ITEM FROM SALE -----
    @Transactional
    public SalesViewDto deleteItem(Long saleId, Long saleItemId) {
        Sales sale = salesRepository.findById(saleId)
                .orElseThrow(() -> new IllegalArgumentException("Sale %d not found".formatted(saleId)));

        boolean removed = sale.getItems().removeIf(i -> saleItemId.equals(i.getId()));
        if (!removed) {
            throw new IllegalArgumentException("Sale item %d not found in sale %d".formatted(saleItemId, saleId));
        }

        recalcTotals(sale);
        Sales saved = salesRepository.save(sale); // JDBC deletes missing children rows
        return toView(saved);
    }

    // ----- helpers -----
    private static BigDecimal nvl(BigDecimal v) { return v != null ? v : BigDecimal.ZERO; }

    private static void recalcTotals(Sales sale) {
        BigDecimal subtotal = sale.getItems().stream()
                .map(SaleItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        sale.setSubtotal(subtotal);

        BigDecimal afterDiscount = subtotal.subtract(nvl(sale.getDiscount()));
        BigDecimal taxAmount = afterDiscount.multiply(nvl(sale.getTaxRate()))
                .divide(BigDecimal.valueOf(100));
        sale.setFinalAmount(afterDiscount.add(taxAmount));
    }

    private static BigDecimal nvl(BigDecimal v1, BigDecimal v2) { return v1 != null ? v1 : v2; }

    private static SalesViewDto toView(Sales sale) {
        List<SaleItemsViewDto> itemViews = sale.getItems().stream()
                .map(i -> new SaleItemsViewDto(
                        i.getId(),
                        i.getProductId(),
                        i.getQuantity(),
                        i.getUnitPrice(),
                        i.getSubtotal()
                )).toList();

        return new SalesViewDto(
                sale.getId(),
                sale.getSubtotal(),
                sale.getDiscount(),
                sale.getTaxRate(),
                sale.getFinalAmount(),
                sale.getPaymentMethod(),
                sale.getCreatedAt(),
                itemViews
        );
    }
}
