package com.AAA.demo.dto;

import java.math.BigDecimal;

public record SaleItemsViewDto(
        Long id,
        Long productId,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal subtotal
) {}
