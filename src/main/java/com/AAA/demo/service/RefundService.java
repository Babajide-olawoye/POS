package com.AAA.demo.service;

import com.AAA.demo.dto.*;
import com.AAA.demo.entities.Product;
import com.AAA.demo.entities.Refund;
import com.AAA.demo.entities.RefundItem;
import com.AAA.demo.repos.ProductRepository;
import com.AAA.demo.repos.RefundRepository;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
public class RefundService {

    private final RefundRepository refundRepo;
    private final ProductRepository productRepo;

    public RefundService(RefundRepository refundRepo, ProductRepository productRepo) {
        this.refundRepo = refundRepo;
        this.productRepo = productRepo;
    }

    @Transactional
    public RefundViewDto createRefund(CreateRefundDto dto) {
        if (dto.items() == null || dto.items().isEmpty()) {
            throw new IllegalArgumentException("Refund must contain at least one item");
        }

        List<RefundItem> items = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (CreateRefundItemDto it : dto.items()) {
            Product p = productRepo.findById(it.productId())
                    .orElseThrow(() -> new IllegalArgumentException("Product %d not found".formatted(it.productId())));
            if (it.quantity() == null || it.quantity() <= 0) {
                throw new IllegalArgumentException("Invalid quantity for product " + it.productId());
            }

            // Price rule (mirrors sales): if product.price > 0 use it; else use provided unitPrice (or 0)
            BigDecimal unitPrice = (p.getPrice().compareTo(BigDecimal.ZERO) > 0)
                    ? p.getPrice()
                    : (it.unitPrice() != null ? it.unitPrice() : BigDecimal.ZERO);

            BigDecimal line = unitPrice.multiply(BigDecimal.valueOf(it.quantity()));

            RefundItem ri = new RefundItem();
            ri.setProductId(p.getId());
            ri.setQuantity(it.quantity());
            ri.setUnitPrice(unitPrice);
            ri.setItemTotal(line);
            items.add(ri);

            total = total.add(line);

            // OPTIONAL: restock
            // productRepo.save(new Product(p.getId(), p.getSku(), p.getName(), p.getPrice(), p.getStock() + it.quantity()));
        }

        Refund refund = new Refund();
        refund.setSaleId(dto.saleId());
        refund.setReason(dto.reason());
        refund.setItems(items);
        refund.setTotalAmount(total);
        refund.setCreatedAt(Instant.now());

        Refund saved = refundRepo.save(refund);
        return toView(saved);
    }

    @Transactional(readOnly = true)
    public RefundViewDto getById(Long id) {
        Refund r = refundRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Refund %d not found".formatted(id)));
        return toView(r);
    }

    @Transactional
    public void delete(Long id) {
        // Deleting the root deletes child rows (because collection is authoritative)
        refundRepo.deleteById(id);
    }

    // mapping
    private static RefundViewDto toView(Refund r) {
        List<RefundItemViewDto> itemViews = r.getItems().stream().map(i ->
                new RefundItemViewDto(i.getId(), i.getProductId(), i.getQuantity(), i.getUnitPrice(), i.getItemTotal())
        ).toList();

        return new RefundViewDto(
                r.getId(), r.getSaleId(), r.getReason(),
                r.getTotalAmount(), r.getCreatedAt(), itemViews
        );
    }
}
