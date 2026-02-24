package com.ecommerce.product.service;

import com.ecommerce.common.tracing.EventEnvelope;
import com.ecommerce.product.dto.ProductResponse;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class ProductEventPublisher {

    private final KafkaTemplate<String, EventEnvelope<Map<String, Object>>> kafkaTemplate;
    private final String productCreatedTopic;
    private final String productUpdatedTopic;
    private final String skuUpdatedTopic;

    public ProductEventPublisher(
            KafkaTemplate<String, EventEnvelope<Map<String, Object>>> kafkaTemplate,
            @Value("${app.kafka.topics.product-created:product.created}") String productCreatedTopic,
            @Value("${app.kafka.topics.product-updated:product.updated}") String productUpdatedTopic,
            @Value("${app.kafka.topics.sku-updated:sku.updated}") String skuUpdatedTopic
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.productCreatedTopic = productCreatedTopic;
        this.productUpdatedTopic = productUpdatedTopic;
        this.skuUpdatedTopic = skuUpdatedTopic;
    }

    public void publishCreated(ProductResponse product, String correlationId) {
        publish(productCreatedTopic, "product.created", product, correlationId);
        publish(skuUpdatedTopic, "sku.updated", product, correlationId);
    }

    public void publishUpdated(ProductResponse product, String correlationId) {
        publish(productUpdatedTopic, "product.updated", product, correlationId);
        publish(skuUpdatedTopic, "sku.updated", product, correlationId);
    }

    private void publish(String topic, String type, ProductResponse product, String correlationId) {
        Map<String, Object> payload = Map.of(
                "id", product.id().toString(),
                "sku", product.sku(),
                "name", product.name(),
                "price", product.price(),
                "currency", product.currency(),
                "active", product.active()
        );

        EventEnvelope<Map<String, Object>> event = new EventEnvelope<>(
                UUID.randomUUID().toString(),
                type,
                OffsetDateTime.now(ZoneOffset.UTC),
                correlationId,
                payload
        );

        kafkaTemplate.send(topic, product.id().toString(), event);
    }
}
