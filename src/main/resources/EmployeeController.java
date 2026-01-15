package com.example.demo.controller;

import com.example.demo.dto.EmployeeDTO.EmployeePartialUpdateDTO;
import com.example.demo.dto.EmployeeDTO.EmployeeRequestDTO;
import com.example.demo.dto.EmployeeDTO.EmployeeResponseDTO;
import com.example.demo.schema.PageDTO;
import com.example.demo.service.EmployeeService;
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



@RestController
@RequiredArgsConstructor
@Slf4j
public class EmployeeController {

    private final EmployeeService employeeService;


    @Operation(summary = "Добавить работника")
    @PostMapping("/employee")
    public EmployeeResponseDTO addEmployee(@Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {
        log.info("Запрос на добавление работника: name={} {}", employeeRequestDTO.getName(), employeeRequestDTO.getSurname());
        EmployeeResponseDTO response = employeeService.addEmployee(employeeRequestDTO);
        log.info("Работник добавлен: id={}, name={} {}", response.getId(), response.getName(), response.getSurname());
        return response;
    }

    @Operation(summary = "Получить работника по параметрам")
    @GetMapping("/employee")
    @ApiResponse(
            responseCode = "200",
            description = "Список работников постранично",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PageDTO.class)
            )
    )
    public Page<EmployeeResponseDTO> getEmployee(@RequestParam(required = false) String name,
                                                 @RequestParam(required = false) String surname,
                                                 @RequestParam(defaultValue = "0") @Min(0) int page,
                                                 @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size,
                                                 @RequestParam(required = false) String sortBy,
                                                 @RequestParam(defaultValue = "asc") String direction) {
        log.debug("Запрос на получение работников: name={}, surname={}, sortBy={}, direction={}, page={}, size={}",
                name, surname, sortBy, direction, page, size);

        Page<EmployeeResponseDTO> result = employeeService.getAllEmployee(name, surname, page, size, sortBy, direction);

        log.info("Найдено {} работников (page={}, size={})", result.getTotalElements(), page, size);
        return result;

    }

    @Operation(summary = "Получить работника по id")
    @GetMapping("/employee/{id}")
    public EmployeeResponseDTO getEmployeeById(@PathVariable("id") Long id) {
        log.info("Запрос на получение работника по id={}", id);
        EmployeeResponseDTO response = employeeService.getEmployeeById(id);
        log.info("Работник найден id={}: name={} {}", id, response.getName(), response.getSurname());
        return response;

    }

    @Operation(summary = "Удалить работника по id")
    @DeleteMapping("/employee/{id}")
    public boolean deleteEmployee(@PathVariable("id") Long id) {
        log.warn("Запрос на удаление работника id={}", id);
        boolean deleted = employeeService.deleteEmployeeById(id);
        log.info("Работник удалён: id={}, result={}", id, deleted);
        return deleted;

    }

    @Operation(summary = "Изменить данные работника")
    @PatchMapping("/employee/{id}")
    public EmployeeResponseDTO partialUpdateEmployee(@PathVariable("id") Long id,
                                                     @Valid @RequestBody EmployeePartialUpdateDTO partialRequestDTO) {
        log.info("Запрос на частичное обновление работника id={}: name={}, surname={}",
                id, partialRequestDTO.getName(), partialRequestDTO.getSurname());

        EmployeeResponseDTO response = employeeService.partialUpdateEmployee(id, partialRequestDTO);

        log.info("Работник обновлён id={}: name={} {}", id, response.getName(), response.getSurname());
        return response;

    }
}
