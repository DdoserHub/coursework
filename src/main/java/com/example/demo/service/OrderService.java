package com.example.demo.service;

import com.example.demo.dto.OrderDTO.*;
import com.example.demo.entity.Client;
import com.example.demo.entity.Item;
import com.example.demo.entity.Order;
import com.example.demo.enums.OrderStatus;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.OrderMapper;
import com.example.demo.repository.ClientRepository;
import com.example.demo.repository.ItemRepository;
import com.example.demo.repository.OrderRepository;
import com.example.demo.specification.OrderSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ClientRepository clientRepository;
    private final OrderMapper orderMapper;
    private final ItemRepository itemRepository;


    public Order getOrderOrThrow(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Заказа с id: " + id + " не найден"));
    }
    
    @Transactional
    public OrderResponseDTO addOrder(OrderRequestDTO orderRequestDTO) {
        Client currentClient = clientRepository.findById(orderRequestDTO.getClientId())
                .orElseThrow(() -> new NotFoundException(
                        "Клиент с id: " + orderRequestDTO.getClientId() + " не найден"));

        Order newOrder = orderMapper.toOrder(orderRequestDTO, currentClient);
        orderRepository.save(newOrder);
        return orderMapper.toOrderResponseDTO(newOrder);
    }

    @Transactional
    public OrderResponseDTO addOrderItem(Long id, OrderAddItemsRequest orderAddItemsRequest) {
        Order currentOrder = getOrderOrThrow(id);

        Set<Item> itemsToAdd = new HashSet<>(itemRepository.findAllById(orderAddItemsRequest.getItemIds()));

        if (itemsToAdd.size() != orderAddItemsRequest.getItemIds().size()) {
            throw new NotFoundException("Один или несколько товаров не найдены");
        }
        currentOrder.getItems().addAll(itemsToAdd);
        orderRepository.save(currentOrder);
        return orderMapper.toOrderResponseDTO(currentOrder);
    }

    @Transactional
    public OrderResponseDTO updateOrderStatus(Long id, OrderStatusDTO status) {
        Order currentOrder = getOrderOrThrow(id);
        currentOrder.setStatus(status.getStatus());
        orderRepository.save(currentOrder);
        return orderMapper.toOrderResponseDTO(currentOrder);
    }

    public boolean deleteOrder(Long id) {
        getOrderOrThrow(id);
        orderRepository.deleteById(id);
        return true;
    }

    public boolean deleteOrderItem(Long id, OrderAddItemsRequest orderAddItemsRequest) {
        Order currentOrder = getOrderOrThrow(id);

        Set<Item> itemsToRemove = new HashSet<>(itemRepository.findAllById(orderAddItemsRequest.getItemIds()));

        if (itemsToRemove.size() != orderAddItemsRequest.getItemIds().size()) {
            throw new NotFoundException("Один или несколько товаров не найдены");
        }

        if (!currentOrder.getItems().containsAll(itemsToRemove)) {
            throw new NotFoundException("Один или несколько товаров отсутствуют в заказе");
        }

        currentOrder.getItems().removeAll(itemsToRemove);
        orderRepository.save(currentOrder);

        return true;
    }

    public Page<OrderResponseDTO> getOrder(OrderStatus status, LocalDateTime createdAt,
                                           Long itemId,
                                           String directional, String sortBy,
                                           int page, int size) {

        String sortField = sortBy != null && sortBy.equals("createdAt")
                ? sortBy
                : "id";

        Sort.Direction sortDirection = directional != null && directional.equals("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortField));

        Specification<Order> specification = OrderSpecification.filterBy(status, createdAt, itemId);

        return orderRepository.findAll(specification, pageable).map(orderMapper::toOrderResponseDTO);
    }

    public OrderResponseDTO getOrderById(Long id) {
        Order currentOrder = getOrderOrThrow(id);
        return orderMapper.toOrderResponseDTO(currentOrder);
    }
}
