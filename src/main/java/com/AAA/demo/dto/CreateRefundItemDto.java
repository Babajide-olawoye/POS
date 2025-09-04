// CreateRefundItemDto.java
package com.AAA.demo.dto;
import java.math.BigDecimal;

public record CreateRefundItemDto(
        Long productId,
        Integer quantity,
        BigDecimal unitPrice // if null, you can fetch from Product.price
) {}