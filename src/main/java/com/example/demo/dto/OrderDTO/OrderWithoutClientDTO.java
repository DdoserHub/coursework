package com.example.demo.dto.OrderDTO;

import com.example.demo.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Schema(description = "Информация о заказе без данных клиента")
public class OrderWithoutClientDTO {

    @Schema(description = "id заказа", example = "1001")
    private Long id;

    @Schema(
            description = "Дата и время создания заказа",
            type = "string",
            format = "date-time",
            example = "2025-09-13T10:15:30"
    )
    private LocalDateTime createdAt;

    @Schema(description = "Статус заказа", example = "IN_PROGRESS")
    private OrderStatus status;
}
