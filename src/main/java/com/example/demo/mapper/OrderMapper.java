package com.example.demo.mapper;

import com.example.demo.dto.OrderDTO.OrderRequestDTO;
import com.example.demo.dto.OrderDTO.OrderResponseDTO;
import com.example.demo.entity.Client;
import com.example.demo.entity.Order;
import java.lang.annotation.Target;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.TargetType;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "client", source = "client")
    @Mapping(target = "id", ignore = true)
    Order toOrder(OrderRequestDTO orderRequestDTO, Client client);

    OrderResponseDTO toOrderResponseDTO(Order order);
}
