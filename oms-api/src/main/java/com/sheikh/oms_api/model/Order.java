package com.sheikh.oms_api.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.Hibernate;

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
    //Hibernate needs a no-argument constructor so it can create objects when reading from the database.
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
    //update status
    public void sendToRisk() {
    if (status != OrderStatus.NEW) {
        throw new IllegalStateException("Only NEW orders can be sent to risk");
    }

    status = OrderStatus.PENDING_RISK;
    updatedAt = LocalDateTime.now();
    }

    public void approveRisk() {
        if (status != OrderStatus.PENDING_RISK) {
            throw new IllegalStateException("Only PENDING_RISK orders can be approved");
        }

        status = OrderStatus.RISK_APPROVED;
        updatedAt = LocalDateTime.now();
    }

    public void rejectRisk() {
        if (status != OrderStatus.PENDING_RISK) {
            throw new IllegalStateException("Only PENDING_RISK orders can be rejected");
        }

        status = OrderStatus.RISK_REJECTED;
        updatedAt = LocalDateTime.now();
    }

    public void sendToExecution() {
        if (status != OrderStatus.RISK_APPROVED) {
            throw new IllegalStateException("Only RISK_APPROVED orders can be sent to execution");
        }

        status = OrderStatus.SENT_TO_EXECUTION;
        updatedAt = LocalDateTime.now();
    }

    public void partiallyFill() {
        if (status != OrderStatus.SENT_TO_EXECUTION) {
            throw new IllegalStateException("Only SENT_TO_EXECUTION orders can be partially filled");
        }

        status = OrderStatus.PARTIALLY_FILLED;
        updatedAt = LocalDateTime.now();
    }

    public void fill() {
        if (status != OrderStatus.SENT_TO_EXECUTION &&
            status != OrderStatus.PARTIALLY_FILLED) {
            throw new IllegalStateException("Only executable orders can be filled");
        }

        status = OrderStatus.FILLED;
        updatedAt = LocalDateTime.now();
    }
}