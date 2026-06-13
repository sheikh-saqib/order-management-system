package com.sheikh.oms_api.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_events")
public class OrderEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    @Enumerated(EnumType.STRING)
    private OrderEventType eventType;

    private LocalDateTime createdAt;

    public OrderEvent(Long orderId, OrderEventType eventType) {
        this.orderId = orderId;
        this.eventType = eventType;
        this.createdAt = LocalDateTime.now();
    }
    //Hibernate needs a no-argument constructor so it can create objects when reading from the database.
    protected OrderEvent() {
    }
    public Long getId() {
        return id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public OrderEventType getEventType() {
        return eventType;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}