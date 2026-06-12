package com.sheikh.oms_api.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String symbol;

    @Enumerated(EnumType.STRING)
    private OrderSide side;

    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    private Integer quantity;

    @Column(precision = 19, scale = 4)
    private BigDecimal limitPrice;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    protected Order() {
    }

    public Order(String symbol, OrderSide side, OrderType orderType, Integer quantity, BigDecimal limitPrice) {
        this.symbol = symbol;
        this.side = side;
        this.orderType = orderType;
        this.quantity = quantity;
        this.limitPrice = limitPrice;
        this.status = OrderStatus.NEW;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    public void cancel() {
        if (this.status == OrderStatus.CANCELLED) {
            throw new IllegalStateException("Order is already cancelled");
        }

        this.status = OrderStatus.CANCELLED;
        this.updatedAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getSymbol() {
        return symbol;
    }

    public OrderSide getSide() {
        return side;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public BigDecimal getLimitPrice() {
        return limitPrice;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
}