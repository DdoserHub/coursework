package com.example.demo.dto.EmployeeDTO;

import com.example.demo.enums.EmployeeRole;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeResponseDTO {

    @Schema(description = "id работника", example = "1")
    private Long id;

    @Schema(description = "Имя сотрудника", example = "Алексей")
    private String name;

    @Schema(description = "Фамилия сотрудника", example = "Смирнов")
    private String surname;

    @Schema(description = "Email сотрудника", example = "ivan@example.com")
    private String email;

    @Schema(description = "Роль сотрудника", example = "ADMIN")
    private EmployeeRole employeeRole;
}
