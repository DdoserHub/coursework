package com.example.demo.dto.EmployeeDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeePartialUpdateDTO {

    @Schema(description = "Имя сотрудника", example = "Алексей")
    @Size(min = 2, max = 50)
    private String name;

    @Schema(description = "Фамилия сотрудника", example = "Смирнов")
    @Size(min = 2, max = 50)
    private String surname;
}
