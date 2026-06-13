package com.sheikh.oms_api.repository;

import com.sheikh.oms_api.model.OrderEvent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderEventRepository
        extends JpaRepository<OrderEvent, Long> {

    List<OrderEvent> findByOrderId(Long orderId);
}