package com.example.demo.dto.OrderDTO;

import com.example.demo.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public class OrderWithoutClientDTO {
    private Long id;
    private Instant createdAt;
    private OrderStatus status;
}
