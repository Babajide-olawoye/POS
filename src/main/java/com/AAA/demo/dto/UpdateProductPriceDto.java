package com.AAA.demo.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public record UpdateProductPriceDto (
    @NotBlank
    String name,

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    BigDecimal price

    )
{}