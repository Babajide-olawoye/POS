package com.AAA.demo.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class UpdateProductPriceDto {
    @NotBlank
    private String name;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal costPrice;

    public String getName() {
        return name;
    }

    public BigDecimal getCostPrice() {
        return costPrice;
    }
}