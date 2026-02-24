CREATE TABLE products (
    id BINARY(16) NOT NULL,
    sku VARCHAR(64) NOT NULL,
    name VARCHAR(200) NOT NULL,
    description VARCHAR(2000) NOT NULL,
    price DECIMAL(18,2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    active BIT(1) NOT NULL,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_products_sku UNIQUE (sku)
);
