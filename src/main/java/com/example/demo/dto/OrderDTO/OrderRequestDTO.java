package com.example.demo.dto.OrderDTO;

import com.example.demo.enums.OrderStatus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class OrderRequestDTO {

    @NotNull
    private Long clientId;

    private OrderStatus status;
}
