// Sale.java (AGGREGATE ROOT)
package com.AAA.demo.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Table("refunds")
public class Refund {
    @Id
    private Long id;

    // Optional linkage to original sale (if you track it)
    @Column("sale_id")
    private Long saleId;

    @Column("total_amount")
    private BigDecimal totalAmount;

    @Column("reason")
    private String reason;

    @Column("created_at")
    private Instant createdAt;

    // refund_items.refund_id is the FK
    @MappedCollection(idColumn = "refund_id", keyColumn = "line_no")
    private List<RefundItem> items = new ArrayList<>();

    // getters/setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getSaleId() { return saleId; }
    public void setSaleId(Long saleId) { this.saleId = saleId; }
    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }
    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public List<RefundItem> getItems() { return items; }
    public void setItems(List<RefundItem> items) { this.items = items; }
}