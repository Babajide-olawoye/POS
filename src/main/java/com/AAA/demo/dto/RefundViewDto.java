// RefundViewDto.java
package com.AAA.demo.dto;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

public record RefundViewDto(
        Long id,
        Long saleId,
        String reason,
        BigDecimal totalAmount,
        Instant createdAt,
        List<RefundItemViewDto> items
) {}
