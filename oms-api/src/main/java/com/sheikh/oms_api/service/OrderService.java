package com.sheikh.oms_api.service;

import com.sheikh.oms_api.Interface.IOrderService;
import com.sheikh.oms_api.dto.CreateOrderRequest;
import com.sheikh.oms_api.dto.OrderEventResponse;
import com.sheikh.oms_api.dto.OrderResponse;
import com.sheikh.oms_api.exception.OrderNotFoundException;
import com.sheikh.oms_api.model.Order;
import com.sheikh.oms_api.model.OrderEvent;
import com.sheikh.oms_api.model.OrderEventType;
import com.sheikh.oms_api.model.OrderType;
import com.sheikh.oms_api.repository.OrderEventRepository;
import com.sheikh.oms_api.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService implements IOrderService {

    private final OrderRepository orderRepository;
    private final OrderEventRepository orderEventRepository;

    public OrderService(OrderRepository orderRepository, OrderEventRepository orderEventRepository) {
        this.orderRepository = orderRepository;
        this.orderEventRepository = orderEventRepository;
    }

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
        orderEventRepository.save(
            new OrderEvent(
                    savedOrder.getId(),
                    OrderEventType.ORDER_CREATED
            )
        );
        return OrderResponse.from(savedOrder);
    }

    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(OrderResponse::from)
                .toList();
    }

    public OrderResponse getOrderById(Long id) {
        Order order = findOrderById(id);
        return OrderResponse.from(order);
    }

    public OrderResponse cancelOrder(Long id) {
        Order order = findOrderById(id);
        order.cancel();

        Order savedOrder = orderRepository.save(order);
        orderEventRepository.save(
                    new OrderEvent(
                            savedOrder.getId(),
                            OrderEventType.ORDER_CANCELLED
                    )
                );
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
    public List<OrderEventResponse> getOrderEvents(Long id) {

        findOrderById(id);

        return orderEventRepository.findByOrderId(id)
        .stream()
        .map(OrderEventResponse::from)
        .toList();
    }

    public OrderResponse sendToRisk(Long id) {
    Order order = findOrderById(id);
    order.sendToRisk();

    Order savedOrder = orderRepository.save(order);
    recordEvent(savedOrder.getId(), OrderEventType.ORDER_SENT_TO_RISK);

    return OrderResponse.from(savedOrder);
    }

    public OrderResponse approveRisk(Long id) {
        Order order = findOrderById(id);
        order.approveRisk();

        Order savedOrder = orderRepository.save(order);
        recordEvent(savedOrder.getId(), OrderEventType.ORDER_APPROVED);

        return OrderResponse.from(savedOrder);
    }

    public OrderResponse rejectRisk(Long id) {
        Order order = findOrderById(id);
        order.rejectRisk();

        Order savedOrder = orderRepository.save(order);
        recordEvent(savedOrder.getId(), OrderEventType.ORDER_REJECTED);

        return OrderResponse.from(savedOrder);
    }

    public OrderResponse sendToExecution(Long id) {
        Order order = findOrderById(id);
        order.sendToExecution();

        Order savedOrder = orderRepository.save(order);
        recordEvent(savedOrder.getId(), OrderEventType.ORDER_SENT_TO_EXECUTION);

        return OrderResponse.from(savedOrder);
    }

    public OrderResponse partiallyFill(Long id) {
        Order order = findOrderById(id);
        order.partiallyFill();

        Order savedOrder = orderRepository.save(order);
        recordEvent(savedOrder.getId(), OrderEventType.ORDER_PARTIALLY_FILLED);

        return OrderResponse.from(savedOrder);
    }

    public OrderResponse fill(Long id) {
        Order order = findOrderById(id);
        order.fill();

        Order savedOrder = orderRepository.save(order);
        recordEvent(savedOrder.getId(), OrderEventType.ORDER_FILLED);

        return OrderResponse.from(savedOrder);
    }

    private void recordEvent(Long orderId, OrderEventType eventType) {
        orderEventRepository.save(new OrderEvent(orderId, eventType));
    }

    

}