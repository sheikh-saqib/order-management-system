package com.sheikh.oms_api.dto;

import com.sheikh.oms_api.model.OrderEvent;
import com.sheikh.oms_api.model.OrderEventType;

import java.time.LocalDateTime;

public record OrderEventResponse(
        Long id,
        Long orderId,
        OrderEventType eventType,
        LocalDateTime createdAt
) {
    public static OrderEventResponse from(OrderEvent event) {
        return new OrderEventResponse(
                event.getId(),
                event.getOrderId(),
                event.getEventType(),
                event.getCreatedAt()
        );
    }
}