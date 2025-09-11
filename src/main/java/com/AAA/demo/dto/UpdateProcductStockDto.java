package com.AAA.demo.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UpdateProcductStockDto(
        @NotBlank
        String name,

        @NotNull
        @Min(0)
        int stock

) {}
