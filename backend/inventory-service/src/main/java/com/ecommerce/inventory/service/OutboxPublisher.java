package com.ecommerce.inventory.service;

import com.ecommerce.common.tracing.EventEnvelope;
import com.ecommerce.inventory.domain.OutboxEvent;
import com.ecommerce.inventory.repository.OutboxEventRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class OutboxPublisher {

    private static final TypeReference<EventEnvelope<Map<String, Object>>> EVENT_TYPE = new TypeReference<>() {
    };

    private final OutboxEventRepository outboxEventRepository;
    private final KafkaTemplate<String, EventEnvelope<Map<String, Object>>> kafkaTemplate;
    private final ObjectMapper objectMapper;

    public OutboxPublisher(OutboxEventRepository outboxEventRepository,
                           KafkaTemplate<String, EventEnvelope<Map<String, Object>>> kafkaTemplate,
                           ObjectMapper objectMapper) {
        this.outboxEventRepository = outboxEventRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Scheduled(fixedDelayString = "${app.outbox.publisher-delay-ms:5000}")
    @Transactional
    public void publishPendingEvents() {
        List<OutboxEvent> pendingEvents = outboxEventRepository.findTop100ByPublishedAtIsNullOrderByOccurredAtAsc();
        for (OutboxEvent event : pendingEvents) {
            EventEnvelope<Map<String, Object>> envelope = deserialize(event.getPayload());
            kafkaTemplate.send(event.getTopic(), event.getKey(), envelope);
            event.setPublishedAt(Instant.now());
            outboxEventRepository.save(event);
        }
    }

    private EventEnvelope<Map<String, Object>> deserialize(String payload) {
        try {
            return objectMapper.readValue(payload, EVENT_TYPE);
        } catch (Exception exception) {
            throw new IllegalStateException("Could not deserialize outbox event", exception);
        }
    }
}
