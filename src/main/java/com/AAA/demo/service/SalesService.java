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

/**
 * Handles business logic around sales and their line items.
 * <p>
 * Provides operations to create sales transactions, add and remove items,
 * and keeps monetary totals consistent.
 */
@Service
public class SalesService {
    private final SalesRepository salesRepository;
    private final ProductRepository productRepo;

    public SalesService(SalesRepository salesRepository, ProductRepository productRepo) {
        this.salesRepository = salesRepository;
        this.productRepo = productRepo;
    }

    /**
     * Creates a new {@link Sales} record along with its line items.
     *
     * @param dto details describing the sale and its items
     * @return a view of the persisted sale
     * @throws IllegalArgumentException if no items are supplied or data is invalid
     */
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
            si.setItemTotal(lineTotal);
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

    /**
     * Adds a single item to an existing sale and updates totals.
     *
     * @param saleId identifier of the sale to update
     * @param dto    new item information
     * @return updated sale view
     * @throws IllegalArgumentException if the sale or product cannot be found
     */
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
        si.setItemTotal(lineTotal);

        sale.getItems().add(si);
        // update totals
        recalcTotals(sale);

        Sales saved = salesRepository.save(sale); // collection is authoritative; insert new row
        return toView(saved);
    }

    /**
     * Deletes an item from the specified sale.
     *
     * @param saleId      sale identifier
     * @param saleItemId  line item identifier
     * @return updated sale view after the item is removed
     * @throws IllegalArgumentException if the sale or item cannot be found
     */
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

    /**
     * Recalculates subtotal, discount, tax, and final amount for a sale based on
     * its current list of items and financial adjustments.
     *
     * @param sale the sale to update
     */
    private static void recalcTotals(Sales sale) {
        BigDecimal subtotal = sale.getItems().stream()
                .map(SaleItem::getItemTotal)
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
                        i.getItemTotal()
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
