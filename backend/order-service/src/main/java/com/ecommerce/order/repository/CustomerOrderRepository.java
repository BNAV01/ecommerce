package com.ecommerce.order.repository;

import com.ecommerce.order.domain.CustomerOrder;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, UUID> {
}
