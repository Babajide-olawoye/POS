// CreateRefundItemDto.java
package com.AAA.demo.dto;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateRefundItemDto(
        @NotNull(message = "Product must not be empty")
        Long productId,
        @Min(value = 1, message = "Quantity must be 1 or more")
        Integer quantity,
        BigDecimal unitPrice // if null, you can fetch from Product.price
) {}