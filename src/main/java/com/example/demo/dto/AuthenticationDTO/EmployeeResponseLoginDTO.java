package com.example.demo.dto.AuthenticationDTO;

import com.example.demo.enums.EmployeeRole;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
@AllArgsConstructor
public class EmployeeResponseLoginDTO {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private EmployeeRole role;
}