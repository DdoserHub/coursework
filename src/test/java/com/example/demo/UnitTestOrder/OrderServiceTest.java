package com.example.demo.UnitTestOrder;

import com.example.demo.dto.OrderDTO.OrderAddItemsRequest;
import com.example.demo.dto.OrderDTO.OrderRequestDTO;
import com.example.demo.dto.OrderDTO.OrderResponseDTO;
import com.example.demo.dto.OrderDTO.OrderStatusDTO;
import com.example.demo.entity.Client;
import com.example.demo.entity.Item;
import com.example.demo.entity.Order;
import com.example.demo.enums.OrderStatus;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @InjectMocks
    private OrderService orderService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private OrderMapper orderMapper;

    private OrderRequestDTO request;
    private OrderStatusDTO statusDTO;
    private OrderAddItemsRequest addItemsRequest;
    private OrderResponseDTO response;
    private Client client;
    private Item item1;
    private Item item2;
    private Order order;

    @BeforeEach
    void initFixtures() {
        request = new OrderRequestDTO();
        request.setClientId(1L);
        request.setStatus(OrderStatus.PROCESSING);

        statusDTO = new OrderStatusDTO();
        statusDTO.setStatus(OrderStatus.COMPLETED);

        addItemsRequest = new OrderAddItemsRequest();
        addItemsRequest.setItemIds(List.of(100L, 101L));

        response = new OrderResponseDTO();
        response.setId(10L);
        response.setStatus(OrderStatus.PROCESSING);

        client = new Client();
        client.setId(1L);

        item1 = new Item();
        item1.setId(100L);

        item2 = new Item();
        item2.setId(101L);

        order = new Order();
        order.setId(10L);
        order.setStatus(OrderStatus.PROCESSING);
        order.setClient(client);
        order.setItems(new HashSet<>());
    }

    @Test
    void addOrder_shouldReturnResponseDTO() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(orderMapper.toOrder(request, client)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.toOrderResponseDTO(order)).thenReturn(response);

        OrderResponseDTO result = orderService.addOrder(request);

        assertEquals(response, result);
        verify(clientRepository).findById(1L);
        verify(orderRepository).save(order);
        verify(orderMapper).toOrderResponseDTO(order);
    }

    @Test
    void addOrder_shouldThrowNotFoundException_whenClientMissing() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> orderService.addOrder(request));

        verify(clientRepository).findById(1L);
        verifyNoInteractions(orderRepository);
    }

    @Test
    void addOrderItem_shouldReturnResponseDTO() {
        when(orderRepository.findById(10L)).thenReturn(Optional.of(order));
        when(itemRepository.findAllById(addItemsRequest.getItemIds())).thenReturn(List.of(item1, item2));
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.toOrderResponseDTO(order)).thenReturn(response);

        OrderResponseDTO result = orderService.addOrderItem(10L, addItemsRequest);

        assertEquals(response, result);
        verify(orderRepository).findById(10L);
        verify(itemRepository).findAllById(addItemsRequest.getItemIds());
        verify(orderRepository).save(order);
        verify(orderMapper).toOrderResponseDTO(order);
    }

    @Test
    void updateOrderStatus_shouldReturnResponseDTO() {
        when(orderRepository.findById(10L)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);
        when(orderMapper.toOrderResponseDTO(order)).thenReturn(response);

        OrderResponseDTO result = orderService.updateOrderStatus(10L, statusDTO);

        assertEquals(response, result);
        verify(orderRepository).findById(10L);
        verify(orderRepository).save(order);
        verify(orderMapper).toOrderResponseDTO(order);
    }

    @Test
    void deleteOrder_shouldReturnTrue() {
        when(orderRepository.findById(10L)).thenReturn(Optional.of(order));

        boolean result = orderService.deleteOrder(10L);

        assertTrue(result);
        verify(orderRepository).findById(10L);
    }

    @Test
    void deleteOrder_shouldThrowNotFoundException() {
        when(orderRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> orderService.deleteOrder(10L));

        verify(orderRepository).findById(10L);
    }

    @Test
    void getOrder_shouldReturnPage() {
        Page<Order> page = new PageImpl<>(List.of(order));
        when(orderRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        when(orderMapper.toOrderResponseDTO(order)).thenReturn(response);

        Page<OrderResponseDTO> result = orderService.getOrder(
                OrderStatus.PROCESSING, null, 100L, "asc", "id", 0, 10
        );

        assertEquals(1, result.getTotalElements());
        assertEquals(List.of(response), result.getContent());
        verify(orderRepository).findAll(any(Specification.class), any(Pageable.class));
        verify(orderMapper).toOrderResponseDTO(order);
    }

    @Test
    void getOrderById_shouldReturnResponseDTO() {
        when(orderRepository.findById(10L)).thenReturn(Optional.of(order));
        when(orderMapper.toOrderResponseDTO(order)).thenReturn(response);

        OrderResponseDTO result = orderService.getOrderById(10L);

        assertEquals(response, result);
        verify(orderRepository).findById(10L);
        verify(orderMapper).toOrderResponseDTO(order);
    }

    @Test
    void getOrderById_shouldThrowNotFoundException() {
        when(orderRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> orderService.getOrderById(10L));

        verify(orderRepository).findById(10L);
    }
}
