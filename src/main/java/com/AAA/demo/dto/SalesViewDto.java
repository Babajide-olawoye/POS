package com.AAA.demo.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record SalesViewDto(
        Long id,
        BigDecimal subtotal,
        BigDecimal discount,
        BigDecimal taxRate,
        BigDecimal finalAmount,
        String paymentMethod,
        Instant createdAt,
        List<SaleItemsViewDto> items
) {}

