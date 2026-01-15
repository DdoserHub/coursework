package com.example.demo.dto.OrderDTO;

import com.example.demo.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Запрос на изменение статуса заказа")
public class OrderStatusDTO {

    @NotNull
    @Schema(description = "Новый статус заказа", example = "COMPLETED")
    private OrderStatus status;
}
