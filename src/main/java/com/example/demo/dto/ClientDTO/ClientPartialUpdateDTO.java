package com.example.demo.dto.ClientDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ClientPartialUpdateDTO {

    @Schema(description = "Имя клиента", example = "Иван")
    @Size(max = 64)
    private String name;

    @Schema(description = "Фамилия клиента", example = "Иванов")
    @Size(max = 64)
    private String surname;

    @Schema(description = "Email клиента", example = "ivan@example.com")
    @Size(max = 64)
    private String email;

    @Schema(description = "Номер телефона клиента", example = "79005552233")
    @Size(max = 64)
    private String number;
}
