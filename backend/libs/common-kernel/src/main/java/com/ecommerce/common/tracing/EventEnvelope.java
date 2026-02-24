package com.ecommerce.common.tracing;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.OffsetDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record EventEnvelope<T>(
        String eventId,
        String type,
        OffsetDateTime occurredAt,
        String correlationId,
        T payload
) {
}