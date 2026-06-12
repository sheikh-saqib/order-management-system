package com.sheikh.oms_api.Interface;

import com.sheikh.oms_api.dto.CreateOrderRequest;
import com.sheikh.oms_api.dto.OrderResponse;

import java.util.List;

public interface IOrderService {

    OrderResponse createOrder(CreateOrderRequest request);

    List<OrderResponse> getAllOrders();

    OrderResponse getOrderById(Long id);

    OrderResponse cancelOrder(Long id);
}