package com.example.demo.schema;

import com.example.demo.dto.OrderDTO.OrderResponseDTO;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "Страница заказов")
public class PageDTO {

    @ArraySchema(
            schema = @Schema(implementation = OrderResponseDTO.class),
            arraySchema = @Schema(description = "Содержимое страницы (список заказов)")
    )
    private List<OrderResponseDTO> content;

    @Schema(description = "Номер страницы (с 0)", example = "0")
    private int number;

    @Schema(description = "Размер страницы", example = "20")
    private int size;

    @Schema(description = "Общее количество элементов", example = "150")
    private long totalElements;

    @Schema(description = "Общее количество страниц", example = "8")
    private int totalPages;

    @Schema(description = "Есть ли следующая страница", example = "true")
    private boolean hasNext;

    @Schema(description = "Есть ли предыдущая страница", example = "false")
    private boolean hasPrevious;
}

