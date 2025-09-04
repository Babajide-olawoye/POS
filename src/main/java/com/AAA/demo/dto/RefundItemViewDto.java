// RefundItemViewDto.java
package com.AAA.demo.dto;
import java.math.BigDecimal;

public record RefundItemViewDto(
        Long id,
        Long productId,
        Integer quantity,
        BigDecimal unitPrice,
        BigDecimal lineTotal
) {}