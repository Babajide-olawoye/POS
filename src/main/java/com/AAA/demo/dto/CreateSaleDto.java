package com.AAA.demo.dto;

import java.util.List;

public record CreateSaleDto(
        List<CreateSaleItem> items,
        java.math.BigDecimal discount,
        java.math.BigDecimal taxRate,
        String paymentMethod
) {}