package com.example.demo.dto.OrderDTO;

import com.example.demo.entity.Item;
import com.example.demo.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Setter
@Getter
public class OrderDetailsDTO {
    private Long id;
    private Instant createdAt;
    private OrderStatus status;
    private List<Item> items;
}
