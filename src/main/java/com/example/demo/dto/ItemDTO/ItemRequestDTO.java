package com.example.demo.dto.ItemDTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ItemRequestDTO {
    @NotBlank
    String name;

    @NotBlank
    String description;

    @Min(0)
    Integer cost;
}
