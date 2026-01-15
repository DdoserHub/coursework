package com.example.demo.dto.ClientDTO;

import com.example.demo.dto.OrderDTO.OrderWithoutClientDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode
public class ClientWithOrdersResponseDTO {

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

    @Schema(description = "Список заказов клиента")
    private List<OrderWithoutClientDTO> orders;
}
