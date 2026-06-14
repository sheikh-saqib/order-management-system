package com.sheikh.oms_api.controller;

import com.sheikh.oms_api.dto.CreateOrderRequest;
import com.sheikh.oms_api.dto.OrderEventResponse;
import com.sheikh.oms_api.dto.OrderResponse;
import com.sheikh.oms_api.model.OrderEvent;
import com.sheikh.oms_api.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Operation(summary = "Create a new order")
    @PostMapping
    public OrderResponse createOrder(@Valid @RequestBody CreateOrderRequest request) {

        return orderService.createOrder(request);
    }

    @Operation(summary = "Retrieve all orders")
    @GetMapping
    public List<OrderResponse> getAllOrders() {

        return orderService.getAllOrders();
    }

    @Operation(summary = "Retrieve an order by ID")
    @GetMapping("/{id}")
    public OrderResponse getOrderById(@PathVariable Long id) {

        return orderService.getOrderById(id);
    }

    @Operation(summary = "Cancel an existing order")
    @PostMapping("/{id}/cancel")
    public OrderResponse cancelOrder(@PathVariable Long id) {

        return orderService.cancelOrder(id);
    }

    @Operation(summary = "Retrieve audit events for an order")
    @GetMapping("/{id}/events")
    public List<OrderEventResponse> getOrderEvents(@PathVariable Long id) {

        return orderService.getOrderEvents(id);
    }
    
    @Operation(summary = "Send order to risk review")
    @PostMapping("/{id}/send-to-risk")
    public OrderResponse sendToRisk(@PathVariable Long id) {
        return orderService.sendToRisk(id);
    }

    @Operation(summary = "Approve order risk")
    @PostMapping("/{id}/approve-risk")
    public OrderResponse approveRisk(@PathVariable Long id) {
        return orderService.approveRisk(id);
    }

    @Operation(summary = "Reject order risk")
    @PostMapping("/{id}/reject-risk")
    public OrderResponse rejectRisk(@PathVariable Long id) {
        return orderService.rejectRisk(id);
    }

    @Operation(summary = "Send order to execution")
    @PostMapping("/{id}/send-to-execution")
    public OrderResponse sendToExecution(@PathVariable Long id) {
        return orderService.sendToExecution(id);
    }

    @Operation(summary = "Partially fill order")
    @PostMapping("/{id}/partial-fill")
    public OrderResponse partiallyFill(@PathVariable Long id) {
        return orderService.partiallyFill(id);
    }

    @Operation(summary = "Fill order")
    @PostMapping("/{id}/fill")
    public OrderResponse fill(@PathVariable Long id) {
        return orderService.fill(id);
    }
}