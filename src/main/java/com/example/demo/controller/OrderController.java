package com.example.demo.controller;

import com.example.demo.dto.OrderDTO.*;
import com.example.demo.enums.OrderStatus;
import com.example.demo.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/order")
    public OrderResponseDTO addOrder(@Valid @RequestBody OrderRequestDTO orderRequestDTO) {
        return orderService.addOrder(orderRequestDTO);
    }

    @PostMapping("/order/{id}/item")
    public OrderResponseDTO addOrderItem(@PathVariable("id") Long id,
                                         @RequestBody OrderAddItemsRequest orderAddItemsRequest) {
        return orderService.addOrderItem(id, orderAddItemsRequest);
    }

    @PatchMapping("/order/{id}")
    public OrderResponseDTO updateOrderStatus(@PathVariable("id") Long id,
                                              @Valid @RequestBody OrderStatusDTO orderStatusDTO) {
        return orderService.updateOrderStatus(id, orderStatusDTO);
    }

    @DeleteMapping("/order/{id}")
    public boolean deleteOrder(@PathVariable("id") Long id) {
        return orderService.deleteOrder(id);
    }

    @DeleteMapping("/order/{id}/item")
    public boolean deleteOrderItem(@PathVariable("id") Long id,
                                   @RequestBody OrderAddItemsRequest orderAddItemsRequest) {
        return orderService.deleteOrderItem(id, orderAddItemsRequest);
    }

    @GetMapping("/order")
    public Page<OrderResponseDTO> getOrder(@Valid @RequestParam(required = false) OrderStatus status,
                                           @RequestParam(required = false) Long itemId,
                                           @RequestParam(required = false) Instant createdAt,
                                           @RequestParam(defaultValue = "asc") String directional,
                                           @RequestParam(required = false) String sortBy,
                                           @RequestParam(defaultValue = "0") @Min(0) int page,
                                           @RequestParam(defaultValue = "10") @Min(1) @Max(10) int size) {
        return orderService.getOrder(status, createdAt, itemId, directional, sortBy, page, size);
    }

    @GetMapping("/order/{id}")
    public OrderResponseDTO getOrderById(@PathVariable("id") Long id) {
        return orderService.getOrderById(id);
    }
}
