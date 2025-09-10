// CreateRefundDto.java
package com.AAA.demo.dto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CreateRefundDto(
        Long saleId,               // optional
        String reason,             // optional
        @NotEmpty(message = "Sale must contain at least one item")
        List<@Valid CreateRefundItemDto> items
) {}