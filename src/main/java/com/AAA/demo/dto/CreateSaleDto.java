package com.AAA.demo.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;

import java.util.List;

public record CreateSaleDto(
        @NotEmpty(message = "Sale must contain at least one item")
        List<@Valid CreateSaleItemDto> items,
        java.math.BigDecimal discount,
        java.math.BigDecimal taxRate,
        @Pattern(regexp="CASH|CARD|TRANSFER", message="Payment method must be CASH, CARD, or TRANSFER")
        String paymentMethod
) {}