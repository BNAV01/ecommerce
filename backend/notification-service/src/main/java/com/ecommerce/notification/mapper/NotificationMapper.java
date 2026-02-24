package com.ecommerce.notification.mapper;

import com.ecommerce.notification.domain.NotificationLog;
import com.ecommerce.notification.dto.NotificationLogResponse;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {

    public NotificationLogResponse toResponse(NotificationLog entity) {
        return new NotificationLogResponse(
                entity.getId(),
                entity.getRecipient(),
                entity.getSubject(),
                entity.getStatus(),
                entity.getCreatedAt()
        );
    }
}
