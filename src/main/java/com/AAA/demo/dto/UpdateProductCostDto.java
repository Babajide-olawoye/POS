package com.AAA.demo.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record UpdateProductCostDto(
        @NotBlank
        String name,

        @NotNull
        @DecimalMin(value = "0.0", inclusive = false)
        BigDecimal costPrice
) {
}
