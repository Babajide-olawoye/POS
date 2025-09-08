DROP TABLE IF EXISTS refund_items;
DROP TABLE IF EXISTS refunds;
DROP TABLE IF EXISTS sale_items;
DROP TABLE IF EXISTS sales;

CREATE TABLE sales (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    subtotal DECIMAL(10, 2),
    discount DECIMAL(10, 2),
    tax_rate DECIMAL(5, 2),
    final_amount DECIMAL(10, 2),
    payment_method VARCHAR(50),
    created_at TIMESTAMP -- (or DATETIME depending on your DB)
);

CREATE TABLE sale_items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    sale_id BIGINT NOT NULL,
    product_id BIGINT,
    quantity INT,
    unit_price DECIMAL(10, 2),
    subtotal DECIMAL(10, 2),
    FOREIGN KEY (sale_id) REFERENCES sales(id)
);

 ALTER TABLE sale_items ADD COLUMN sales_key INT;

 CREATE TABLE refunds (
     id BIGINT PRIMARY KEY AUTO_INCREMENT,
     sale_id BIGINT NULL,                      -- optional link to the original sale
     total_amount DECIMAL(12,2) NOT NULL,      -- sum of all item totals
     reason VARCHAR(255),                      -- why the refund happened
     created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

     CONSTRAINT fk_refunds_sale FOREIGN KEY (sale_id) REFERENCES sales(id)
 );

CREATE TABLE refund_items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    refund_id BIGINT NOT NULL,                -- link back to refund
    product_id BIGINT NOT NULL,               -- product being refunded
    quantity INT NOT NULL,
    unit_price DECIMAL(12,2) NOT NULL,        -- price per unit at refund time
    item_total DECIMAL(12,2) NOT NULL,        -- quantity * unit_price

    CONSTRAINT fk_refund_items_refund FOREIGN KEY (refund_id) REFERENCES refunds(id) ON DELETE CASCADE,
    CONSTRAINT fk_refund_items_product FOREIGN KEY (product_id) REFERENCES products(id)
);

ALTER TABLE refund_items
  ADD COLUMN line_no INT NOT NULL DEFAULT 0;
