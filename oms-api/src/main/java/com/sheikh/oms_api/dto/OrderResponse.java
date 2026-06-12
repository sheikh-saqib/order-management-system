package com.sheikh.oms_api.dto;

import com.sheikh.oms_api.model.Order;
import com.sheikh.oms_api.model.OrderSide;
import com.sheikh.oms_api.model.OrderStatus;
import com.sheikh.oms_api.model.OrderType;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record OrderResponse(
        Long id,
        String symbol,
        OrderSide side,
        OrderType orderType,
        Integer quantity,
        BigDecimal limitPrice,
        OrderStatus status,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public static OrderResponse from(Order order) {
        return new OrderResponse(
                order.getId(),
                order.getSymbol(),
                order.getSide(),
                order.getOrderType(),
                order.getQuantity(),
                order.getLimitPrice(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }
}