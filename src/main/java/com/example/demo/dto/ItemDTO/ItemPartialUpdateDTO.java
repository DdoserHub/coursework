package com.example.demo.dto.ItemDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemPartialUpdateDTO {

    @Schema(description = "Название товара", example = "Смартфон")
    private String name;

    @Schema(description = "Описание товара", example = "Мобильный телефон с 8 ГБ ОЗУ и 256 ГБ памяти")
    private String description;

    @Schema(description = "Стоимость товара", example = "19990")
    @Min(0)
    private Integer cost;
}
