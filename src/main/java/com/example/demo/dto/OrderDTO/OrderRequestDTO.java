package com.example.demo.dto.OrderDTO;

import com.example.demo.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Запрос на создание заказа")
public class OrderRequestDTO {

    @NotNull
    @Schema(description = "Идентификатор клиента, оформившего заказ", example = "501")
    private Long clientId;

    @NotNull
    @Schema(description = "Статус заказа", example = "В процессе")
    private OrderStatus status;
}
