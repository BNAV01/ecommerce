package com.ecommerce.notification.repository;

import com.ecommerce.notification.domain.NotificationLog;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationLogRepository extends JpaRepository<NotificationLog, UUID> {

    List<NotificationLog> findTop100ByOrderByCreatedAtDesc();
}
