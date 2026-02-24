package com.ecommerce.order.repository;

import com.ecommerce.order.domain.OutboxEvent;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxEventRepository extends JpaRepository<OutboxEvent, UUID> {

    List<OutboxEvent> findTop100ByPublishedAtIsNullOrderByOccurredAtAsc();

    long countByPublishedAtIsNullAndOccurredAtBefore(Instant olderThan);
}
