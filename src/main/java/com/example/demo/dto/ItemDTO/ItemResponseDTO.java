package com.example.demo.dto.ItemDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemResponseDTO {

    @Schema(description = "id товара", example = "1")
    private Long id;

    @Schema(description = "Название товара", example = "Смартфон")
    private String name;

    @Schema(description = "Описание товара", example = "Мобильный телефон с 8 ГБ ОЗУ и 256 ГБ памяти")
    private String description;

    @Schema(description = "Стоимость товара", example = "19990")
    private Integer cost;
}
