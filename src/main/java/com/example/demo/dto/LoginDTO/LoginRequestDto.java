package com.example.demo.dto.LoginDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на аутентификацию пользователя")
public class LoginRequestDto {

    @NotBlank
    @Email
    @Schema(description = "Email пользователя", example = "ivan@example.com")
    private String email;

    @NotBlank
    @Size(min = 6)
    @Schema(description = "Пароль пользователя", example = "password123")
    private String password;
}
