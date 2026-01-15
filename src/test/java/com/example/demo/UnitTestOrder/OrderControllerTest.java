package com.example.demo.UnitTestOrder;

import com.example.demo.controller.OrderController;
import com.example.demo.dto.OrderDTO.OrderAddItemsRequest;
import com.example.demo.dto.OrderDTO.OrderRequestDTO;
import com.example.demo.dto.OrderDTO.OrderResponseDTO;
import com.example.demo.dto.OrderDTO.OrderStatusDTO;
import com.example.demo.enums.OrderStatus;
import com.example.demo.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    private OrderRequestDTO request;
    private OrderResponseDTO response;
    private OrderAddItemsRequest addItemsRequest;
    private OrderStatusDTO statusDTO;

    @BeforeEach
    void initFixtures() {
        request = new OrderRequestDTO();
        request.setClientId(1L);
        request.setStatus(OrderStatus.PROCESSING);

        response = new OrderResponseDTO();
        response.setId(10L);
        response.setStatus(OrderStatus.PROCESSING);
        response.setCreatedAt(LocalDateTime.now());

        addItemsRequest = new OrderAddItemsRequest();
        addItemsRequest.setItemIds(List.of(100L, 101L));

        statusDTO = new OrderStatusDTO();
        statusDTO.setStatus(OrderStatus.COMPLETED);
    }

    @Test
    void addOrder_shouldReturnResponseDTO() {
        when(orderService.addOrder(request)).thenReturn(response);

        OrderResponseDTO result = orderController.addOrder(request);

        assertEquals(response, result);
        verify(orderService).addOrder(request);
    }

    @Test
    void addOrderItem_shouldReturnResponseDTO() {
        when(orderService.addOrderItem(10L, addItemsRequest)).thenReturn(response);

        OrderResponseDTO result = orderController.addOrderItem(10L, addItemsRequest);

        assertEquals(response, result);
        verify(orderService).addOrderItem(10L, addItemsRequest);
    }

    @Test
    void updateOrderStatus_shouldReturnResponseDTO() {
        when(orderService.updateOrderStatus(10L, statusDTO)).thenReturn(response);

        OrderResponseDTO result = orderController.updateOrderStatus(10L, statusDTO);

        assertEquals(response, result);
        verify(orderService).updateOrderStatus(10L, statusDTO);
    }

    @Test
    void deleteOrder_shouldReturnBoolean() {
        when(orderService.deleteOrder(10L)).thenReturn(true);

        boolean result = orderController.deleteOrder(10L);

        assertTrue(result);
        verify(orderService).deleteOrder(10L);
    }

    @Test
    void deleteOrderItem_shouldReturnBoolean() {
        when(orderService.deleteOrderItem(10L, addItemsRequest)).thenReturn(true);

        boolean result = orderController.deleteOrderItem(10L, addItemsRequest);

        assertTrue(result);
        verify(orderService).deleteOrderItem(10L, addItemsRequest);
    }

    @Test
    void getOrder_shouldReturnPage() {
        Page<OrderResponseDTO> page = new PageImpl<>(List.of(response), PageRequest.of(0, 10), 1);

        when(orderService.getOrder(OrderStatus.PROCESSING, null, 100L, "asc", "id", 0, 10))
                .thenReturn(page);

        Page<OrderResponseDTO> result = orderController.getOrder(
                OrderStatus.PROCESSING, 100L, null, "asc", "id", 0, 10
        );

        assertEquals(1, result.getTotalElements());
        assertEquals(page.getContent(), result.getContent());
        verify(orderService).getOrder(OrderStatus.PROCESSING, null, 100L, "asc", "id", 0, 10);
    }

    @Test
    void getOrderById_shouldReturnResponseDTO() {
        when(orderService.getOrderById(10L)).thenReturn(response);

        OrderResponseDTO result = orderController.getOrderById(10L);

        assertEquals(response, result);
        verify(orderService).getOrderById(10L);
    }
}
