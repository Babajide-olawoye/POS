// CreateRefundDto.java
package com.AAA.demo.dto;
import java.util.List;

public record CreateRefundDto(
        Long saleId,               // optional
        String reason,             // optional
        List<CreateRefundItemDto> items
) {}