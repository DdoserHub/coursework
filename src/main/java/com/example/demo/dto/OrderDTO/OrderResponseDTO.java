package com.example.demo.dto.OrderDTO;

import com.example.demo.dto.ClientDTO.ClientResponseDTO;
import com.example.demo.dto.ItemDTO.ItemResponseDTO;
import com.example.demo.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@Schema(description = "Ответ с полной информацией о заказе")
public class OrderResponseDTO {

    @Schema(description = "id заказа", example = "1")
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

    @Schema(
            description = "Информация о клиенте, оформившем заказ",
            implementation = ClientResponseDTO.class
    )
    private ClientResponseDTO client;

    @Schema(
            description = "Список товаров в заказе",
            implementation = ItemResponseDTO.class
    )
    private Set<ItemResponseDTO> items;
}
