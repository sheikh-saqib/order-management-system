package com.sheikh.oms_api.Interface;

import com.sheikh.oms_api.dto.CreateOrderRequest;
import com.sheikh.oms_api.dto.OrderResponse;
import com.sheikh.oms_api.model.OrderEvent;

import java.util.List;

public interface IOrderService {

    OrderResponse createOrder(CreateOrderRequest request);

    List<OrderResponse> getAllOrders();

    OrderResponse getOrderById(Long id);

    OrderResponse cancelOrder(Long id);
    List<OrderEvent> getOrderEvents(Long id);
}