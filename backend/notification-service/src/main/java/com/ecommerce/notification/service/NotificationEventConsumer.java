package com.ecommerce.notification.service;

import com.ecommerce.notification.dto.EmailRequest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventConsumer {

    private final NotificationService notificationService;
    private final ObjectMapper objectMapper;

    public NotificationEventConsumer(NotificationService notificationService, ObjectMapper objectMapper) {
        this.notificationService = notificationService;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(
            topics = {
                    "${app.kafka.topics.order-confirmed:order.confirmed}",
                    "${app.kafka.topics.order-cancelled:order.cancelled}",
                    "${app.kafka.topics.stock-rejected:inventory.stock-rejected}",
                    "${app.kafka.topics.email-requested:notification.email-requested}"
            },
            groupId = "${spring.application.name}"
    )
    public void consume(String message) {
        try {
            JsonNode root = objectMapper.readTree(message);
            String type = root.path("type").asText("");
            JsonNode payload = root.path("payload");

            if ("order.confirmed".equals(type)) {
                String recipient = payload.path("customerEmail").asText("orders@example.local");
                String orderId = payload.path("orderId").asText("unknown");
                notificationService.sendEmail(new EmailRequest(
                        recipient,
                        "Order confirmed",
                        "Your order " + orderId + " was confirmed."
                ));
                return;
            }

            if ("order.cancelled".equals(type)) {
                String recipient = payload.path("customerEmail").asText("orders@example.local");
                String orderId = payload.path("orderId").asText("unknown");
                String reason = payload.path("reason").asText("No reason");
                notificationService.sendEmail(new EmailRequest(
                        recipient,
                        "Order cancelled",
                        "Your order " + orderId + " was cancelled: " + reason
                ));
                return;
            }

            if ("inventory.stock-rejected".equals(type)) {
                String orderId = payload.path("orderId").asText("unknown");
                notificationService.sendEmail(new EmailRequest(
                        "ops@example.local",
                        "Inventory rejected",
                        "Inventory rejected reservation for order " + orderId
                ));
                return;
            }

            if ("notification.email-requested".equals(type)) {
                notificationService.sendEmail(new EmailRequest(
                        payload.path("recipient").asText("noreply@example.local"),
                        payload.path("subject").asText("Notification"),
                        payload.path("body").asText("Empty body")
                ));
            }
        } catch (Exception ignored) {
            // Keep consumer resilient for template bootstrap.
        }
    }
}
