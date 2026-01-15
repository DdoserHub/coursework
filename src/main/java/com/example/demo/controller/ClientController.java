package com.example.demo.controller;

import com.example.demo.dto.ClientDTO.ClientPartialUpdateDTO;
import com.example.demo.dto.ClientDTO.ClientRequestDTO;
import com.example.demo.dto.ClientDTO.ClientResponseDTO;
import com.example.demo.dto.ClientDTO.ClientWithOrdersResponseDTO;
import com.example.demo.schema.PageDTO;
import com.example.demo.service.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @Operation(summary = "Добавить клиента")
    @PostMapping("/client")
    public ClientResponseDTO addClient(@Valid @RequestBody ClientRequestDTO clientRequestDTO) {
        log.info("Запрос на добавление клиента: email={}, name={} {}",
                clientRequestDTO.getEmail(),
                clientRequestDTO.getName(),
                clientRequestDTO.getSurname());

        ClientResponseDTO response = clientService.addClient(clientRequestDTO);

        log.info("Клиент добавлен: email={}, name={} {}",
                response.getEmail(),
                response.getName(),
                response.getSurname());

        return response;
    }

    @Operation(summary = "Изменить данные клиента")
    @PatchMapping("/client/{id}")
    public ClientResponseDTO partialUpdateClient(@PathVariable("id") Long id,
                                                 @Valid @RequestBody ClientPartialUpdateDTO dto) {
        log.info("Запрос на частичное обновление клиента id={}: email={}, name={} {}",
                id, dto.getEmail(), dto.getName(), dto.getSurname());

        ClientResponseDTO response = clientService.partialUpdateClient(id, dto);

        log.info("Клиент обновлён id={}: email={}, name={} {}",
                id, response.getEmail(), response.getName(), response.getSurname());

        return response;
    }

    @Operation(summary = "Удалить клиента по id")
    @DeleteMapping("/client/{id}")
    public boolean deleteClient(@PathVariable("id") Long id) {
        log.warn("Запрос на удаление клиента id={}", id);

        boolean deleted = clientService.deleteClient(id);

        log.info("Клиент удалён: id={}", id);

        return deleted;
    }

    @Operation(summary = "Получить клиентов по параметрам")
    @GetMapping("/client")
    @ApiResponse(
            responseCode = "200",
            description = "Список клиентов постранично",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PageDTO.class)
            )
    )
    public Page<ClientResponseDTO> getClient(@RequestParam(required = false) String name,
                                             @RequestParam(defaultValue = "asc") String directional,
                                             @RequestParam(required = false) String sortBy,
                                             @RequestParam(required = false) String surname,
                                             @RequestParam(required = false) String email,
                                             @RequestParam(required = false) String number,
                                             @RequestParam(defaultValue = "0") @Min(0) int page,
                                             @RequestParam(defaultValue = "10") @Min(1) @Max(10) int size) {
        log.debug("Запрос на получение списка клиентов: name={}, surname={}, email={}, number={}, sortBy={}, direction={}, page={}, size={}",
                name, surname, email, number, sortBy, directional, page, size);

        Page<ClientResponseDTO> clients = clientService.getClient(name, directional, sortBy, surname, email, number, page, size);

        log.info("Найдено {} клиентов (page={}, size={})", clients.getTotalElements(), page, size);

        return clients;
    }

    @Operation(summary = "Получить клиента по id")
    @GetMapping("/client/{id}")
    public ClientWithOrdersResponseDTO getClientById(@PathVariable("id") Long id) {
        log.info("Запрос на получение клиента по id={}", id);

        ClientWithOrdersResponseDTO response = clientService.getClientById(id);

        log.info("Клиент найден id={}: email={}, name={} {}",
                id, response.getEmail(), response.getName(), response.getSurname());

        return response;
    }
}
