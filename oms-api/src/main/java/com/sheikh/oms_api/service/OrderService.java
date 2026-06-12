package com.sheikh.oms_api.service;

import com.sheikh.oms_api.Interface.IOrderService;
import com.sheikh.oms_api.dto.CreateOrderRequest;
import com.sheikh.oms_api.dto.OrderResponse;
import com.sheikh.oms_api.exception.OrderNotFoundException;
import com.sheikh.oms_api.model.Order;
import com.sheikh.oms_api.model.OrderType;
import com.sheikh.oms_api.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {
        validateLimitOrder(request);

        Order order = new Order(
                request.getSymbol().toUpperCase(),
                request.getSide(),
                request.getOrderType(),
                request.getQuantity(),
                request.getLimitPrice()
        );

        Order savedOrder = orderRepository.save(order);

        return OrderResponse.from(savedOrder);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(OrderResponse::from)
                .toList();
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        Order order = findOrderById(id);
        return OrderResponse.from(order);
    }

    @Override
    public OrderResponse cancelOrder(Long id) {
        Order order = findOrderById(id);
        order.cancel();

        Order savedOrder = orderRepository.save(order);

        return OrderResponse.from(savedOrder);
    }

    private Order findOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    private void validateLimitOrder(CreateOrderRequest request) {
        if (request.getOrderType() == OrderType.LIMIT && request.getLimitPrice() == null) {
            throw new IllegalArgumentException("Limit price is required for LIMIT orders");
        }
    }
}