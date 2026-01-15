package com.example.demo.controller;

import com.example.demo.dto.OrderDTO.*;
import com.example.demo.enums.OrderStatus;
import com.example.demo.schema.PageDTO;
import com.example.demo.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;

    @Operation(summary = "Добавить заказ")
    @PostMapping("/order")
    public OrderResponseDTO addOrder(@Valid @RequestBody OrderRequestDTO orderRequestDTO) {
        log.info("Запрос на создание заказа: clientId={}", orderRequestDTO.getClientId());
        OrderResponseDTO response = orderService.addOrder(orderRequestDTO);
        log.info("Заказ создан: id={}, clientId={}", response.getId(), orderRequestDTO.getClientId());
        return response;

    }

    @Operation(summary = "Добавить в заказ товар")
    @PostMapping("/order/{id}/item")
    public OrderResponseDTO addOrderItem(@PathVariable("id") Long id,
                                         @RequestBody OrderAddItemsRequest orderAddItemsRequest) {
        log.info("Запрос на добавление товара в заказ: orderId={}, itemId={}",
                id, orderAddItemsRequest.getItemIds());

        OrderResponseDTO response = orderService.addOrderItem(id, orderAddItemsRequest);

        log.info("Товар добавлен в заказ: orderId={}", id);
        return response;

    }

    @Operation(summary = "Изменить статус заказа")
    @PatchMapping("/order/{id}")
    public OrderResponseDTO updateOrderStatus(@PathVariable("id") Long id,
                                              @Valid @RequestBody OrderStatusDTO orderStatusDTO) {
        log.info("Запрос на изменение статуса заказа: orderId={}, status={}", id, orderStatusDTO.getStatus());
        OrderResponseDTO response = orderService.updateOrderStatus(id, orderStatusDTO);
        log.info("Статус заказа обновлён: orderId={}, status={}", id, response.getStatus());
        return response;

    }

    @Operation(summary = "Удалить заказ по id")
    @DeleteMapping("/order/{id}")
    public boolean deleteOrder(@PathVariable("id") Long id) {
        log.warn("Запрос на удаление заказа id={}", id);
        boolean deleted = orderService.deleteOrder(id);
        log.info("Заказ удалён: id={}, result={}", id, deleted);
        return deleted;

    }

    @Operation(summary = "Удалить из заказа товар")
    @DeleteMapping("/order/{id}/item")
    public boolean deleteOrderItem(@PathVariable("id") Long id,
                                   @RequestBody OrderAddItemsRequest orderAddItemsRequest) {
        log.warn("Запрос на удаление товара из заказа: orderId={}, itemId={}",
                id, orderAddItemsRequest.getItemIds());

        boolean deleted = orderService.deleteOrderItem(id, orderAddItemsRequest);

        log.info("Удаление товара из заказа выполнено: orderId={}, result={}", id, deleted);
        return deleted;

    }

    @Operation(summary = "Получить заказ по параметрам")
    @ApiResponse(
            responseCode = "200",
            description = "Список заказов постранично",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PageDTO.class)
            )
    )
    @GetMapping("/order")
    public Page<OrderResponseDTO> getOrder(@Valid @RequestParam(required = false) OrderStatus status,
                                           @RequestParam(required = false) Long itemId,
                                           @RequestParam(required = false) LocalDateTime createdAt,
                                           @RequestParam(defaultValue = "asc") String directional,
                                           @RequestParam(required = false) String sortBy,
                                           @RequestParam(defaultValue = "0") @Min(0) int page,
                                           @RequestParam(defaultValue = "10") @Min(1) @Max(10) int size) {
        log.debug("Запрос на получение заказов: status={}, itemId={}, createdAt={}, sortBy={}, direction={}, page={}, size={}",
                status, itemId, createdAt, sortBy, directional, page, size);

        Page<OrderResponseDTO> result = orderService.getOrder(status, createdAt, itemId, directional, sortBy, page, size);

        log.info("Найдено {} заказов (page={}, size={})", result.getTotalElements(), page, size);
        return result;

    }

    @Operation(summary = "Получить заказ по id")
    @GetMapping("/order/{id}")
    public OrderResponseDTO getOrderById(@PathVariable("id") Long id) {
        log.info("Запрос на получение заказа по id={}", id);
        OrderResponseDTO response = orderService.getOrderById(id);
        log.info("Заказ найден id={}: status={}", id, response.getStatus());
        return response;

    }
}
