package com.ecommerce.notification.dto;

import java.time.Instant;
import java.util.UUID;

public record NotificationLogResponse(
        UUID id,
        String recipient,
        String subject,
        String status,
        Instant createdAt
) {
}
