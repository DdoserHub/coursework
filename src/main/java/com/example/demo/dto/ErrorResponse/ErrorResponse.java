package com.example.demo.dto.ErrorResponse;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "Ответ с информацией об ошибке")
public class ErrorResponse {

    @Schema(description = "HTTP статус", example = "400")
    private int status;

    @Schema(description = "Тип ошибки", example = "Bad Request")
    private String error;

    @Schema(description = "Сообщение об ошибке", example = "Email must not be blank")
    private String message;
}
