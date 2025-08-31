package com.AAA.demo.dto;

import java.math.BigDecimal;

public record CreateSaleItem(
        Long productId,
        Integer quantity,
        BigDecimal unitPrice
) {}
