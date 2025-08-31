package com.AAA.demo.entities;

// SaleItem.java (VALUE OBJECT owned by Sale)

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;

@Table("sale_items")
public class SaleItem {

    @Id
    private Long id;

    // Reference other aggregate by ID (no @ManyToOne)
    @Column("product_id")
    private Long productId;

    private int quantity;

    @Column("unit_price")
    private BigDecimal unitPrice;

    // Your schema used "subtotal" — keep it, but use BigDecimal for money
    @Column("subtotal")
    private BigDecimal subtotal;

    // ---- getters/setters ----
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }
}
