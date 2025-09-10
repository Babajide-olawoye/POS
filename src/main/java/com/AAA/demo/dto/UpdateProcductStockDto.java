package com.AAA.demo.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateProcductStockDto(
        @NotBlank
        String name,

        @NotNull
        @DecimalMin(value = "0.0", inclusive = false)
        int stock

) {}
