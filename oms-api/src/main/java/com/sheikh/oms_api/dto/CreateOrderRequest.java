package com.sheikh.oms_api.dto;

import com.sheikh.oms_api.model.OrderSide;
import com.sheikh.oms_api.model.OrderType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public class CreateOrderRequest {

    @NotBlank
    private String symbol;

    @NotNull
    private OrderSide side;

    @NotNull
    private OrderType orderType;

    @NotNull
    @Positive
    private Integer quantity;

    private BigDecimal limitPrice;

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
}