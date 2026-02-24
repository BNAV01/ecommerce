package com.ecommerce.inventory.service;

import com.ecommerce.common.tracing.EventEnvelope;
import com.ecommerce.inventory.domain.OutboxEvent;
import com.ecommerce.inventory.repository.OutboxEventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Map;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OutboxService {

    private final OutboxEventRepository outboxEventRepository;
    private final ObjectMapper objectMapper;

    public OutboxService(OutboxEventRepository outboxEventRepository, ObjectMapper objectMapper) {
        this.outboxEventRepository = outboxEventRepository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public void enqueue(String aggregateType,
                        String aggregateId,
                        String eventType,
                        String topic,
                        String key,
                        String correlationId,
                        Map<String, Object> payload) {
        EventEnvelope<Map<String, Object>> envelope = new EventEnvelope<>(
                UUID.randomUUID().toString(),
                eventType,
                OffsetDateTime.now(ZoneOffset.UTC),
                correlationId,
                payload
        );

        OutboxEvent event = new OutboxEvent();
        event.setAggregateType(aggregateType);
        event.setAggregateId(aggregateId);
        event.setEventType(eventType);
        event.setTopic(topic);
        event.setKey(key);
        event.setPayload(serialize(envelope));
        outboxEventRepository.save(event);
    }

    private String serialize(EventEnvelope<Map<String, Object>> envelope) {
        try {
            return objectMapper.writeValueAsString(envelope);
        } catch (JsonProcessingException exception) {
            throw new IllegalStateException("Could not serialize outbox event", exception);
        }
    }
}
