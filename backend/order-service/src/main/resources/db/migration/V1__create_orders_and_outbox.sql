CREATE TABLE orders (
    id BINARY(16) NOT NULL,
    customer_email VARCHAR(255) NOT NULL,
    status VARCHAR(32) NOT NULL,
    total_amount DECIMAL(18,2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    created_at TIMESTAMP(6) NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE order_items (
    id BIGINT NOT NULL AUTO_INCREMENT,
    order_id BINARY(16) NOT NULL,
    sku VARCHAR(64) NOT NULL,
    quantity INT NOT NULL,
    unit_price DECIMAL(18,2) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_order_items_order_id FOREIGN KEY (order_id) REFERENCES orders (id)
);

CREATE TABLE outbox_events (
    id BINARY(16) NOT NULL,
    aggregate_type VARCHAR(64) NOT NULL,
    aggregate_id VARCHAR(64) NOT NULL,
    event_type VARCHAR(128) NOT NULL,
    topic VARCHAR(128) NOT NULL,
    message_key VARCHAR(128) NOT NULL,
    payload LONGTEXT NOT NULL,
    occurred_at TIMESTAMP(6) NOT NULL,
    published_at TIMESTAMP(6) NULL,
    PRIMARY KEY (id),
    INDEX idx_outbox_published_at (published_at),
    INDEX idx_outbox_occurred_at (occurred_at)
);
