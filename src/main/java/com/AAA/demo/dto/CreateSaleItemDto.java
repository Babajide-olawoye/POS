package com.AAA.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateSaleItemDto(
        @NotNull(message = "Product is required")
        Long productId,
        @Min(value = 1, message = "Minimum value must be 1")
        Integer quantity,
        BigDecimal unitPrice
) {}

