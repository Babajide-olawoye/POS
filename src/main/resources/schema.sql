CREATE TABLE sale_items (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    sale_id BIGINT NOT NULL,
    product_id BIGINT,
    quantity INT,
    unit_price DECIMAL(10, 2),
    subtotal DECIMAL(10, 2),
    FOREIGN KEY (sale_id) REFERENCES sales(id)
);

CREATE TABLE sales (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    subtotal DECIMAL(10, 2),
    discount DECIMAL(10, 2),
    tax_rate DECIMAL(5, 2),
    final_amount DECIMAL(10, 2),
    payment_method VARCHAR(50),
    updated_by TIMESTAMP -- (or DATETIME depending on your DB)
);

