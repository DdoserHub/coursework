package com.example.demo.dto.EmployeeDTO;

import com.example.demo.enums.EmployeeRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class EmployeeRequestDTO {

    @NotBlank
    private String name;

    @NotBlank
    private String surname;

    @Email
    private String email;

    @Size(min = 6, message = "Пароль должен быть не менее 6 символов")
    private String password;

    @NotBlank
    private String number;

    @NotNull
    private EmployeeRole employeeRole;
}
