package com.example.demo.dto.OrderDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(description = "Запрос на добавление товаров в заказ")
public class OrderAddItemsRequest {

    @NotEmpty
    @Schema(
            description = "Список идентификаторов товаров для добавления в заказ",
            example = "[101, 102, 103]"
    )
    private List<@NotNull Long> itemIds;
}
