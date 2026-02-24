CREATE TABLE inventory_stock (
    id BINARY(16) NOT NULL,
    sku VARCHAR(64) NOT NULL,
    available_quantity BIGINT NOT NULL,
    reserved_quantity BIGINT NOT NULL,
    updated_at TIMESTAMP(6) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uk_inventory_stock_sku UNIQUE (sku)
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
