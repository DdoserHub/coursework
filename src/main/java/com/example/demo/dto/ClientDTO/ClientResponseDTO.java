package com.example.demo.dto.ClientDTO;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class ClientResponseDTO {

    @Schema(description = "id клиента", example = "1")
    private Long id;

    @Schema(description = "Имя клиента", example = "Иван")
    private String name;

    @Schema(description = "Фамилия клиента", example = "Иванов")
    private String surname;

    @Schema(description = "Email клиента", example = "ivan@example.com")
    private String email;

    @Schema(description = "Номер телефона клиента", example = "79005552233")
    private String number;
}
