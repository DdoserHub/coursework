package com.example.demo.dto.OrderDTO;

import com.example.demo.dto.ClientDTO.ClientResponseDTO;
import com.example.demo.dto.ItemDTO.ItemResponseDTO;
import com.example.demo.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.Set;

@Getter
@Setter
public class OrderResponseDTO {
    private Instant createdAt;
    private OrderStatus status;
    private ClientResponseDTO client;
    private Set<ItemResponseDTO> items;
}
