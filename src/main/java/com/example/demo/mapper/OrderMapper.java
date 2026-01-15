package com.example.demo.mapper;

import com.example.demo.dto.OrderDTO.OrderRequestDTO;
import com.example.demo.dto.OrderDTO.OrderResponseDTO;
import com.example.demo.entity.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    Order toOrder(OrderRequestDTO orderRequestDTO);

    OrderResponseDTO toOrderResponseDTO(Order order);
}
