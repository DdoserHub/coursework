package com.example.demo.controller;

import com.example.demo.dto.ItemDTO.ItemPartialUpdateDTO;
import com.example.demo.dto.ItemDTO.ItemRequestDTO;
import com.example.demo.dto.ItemDTO.ItemResponseDTO;
import com.example.demo.schema.PageDTO;
import com.example.demo.service.ItemService;
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
public class ItemController {

    private final ItemService itemService;

    @Operation(summary = "Добавить товар")
    @PostMapping("/item")
    public ItemResponseDTO addItem(@Valid @RequestBody ItemRequestDTO itemRequestDTO) {
        log.info("Запрос на добавление товара: name={}, cost={}", itemRequestDTO.getName(), itemRequestDTO.getCost());
        ItemResponseDTO response = itemService.addItem(itemRequestDTO);
        log.info("Товар добавлен: id={}, name={}, cost={}", response.getId(), response.getName(), response.getCost());
        return response;

    }

    @Operation(summary = "Изменить товар")
    @PatchMapping("/item/{id}")
    public ItemResponseDTO partialUpdateItem(@PathVariable("id") Long id,
                                             @RequestBody ItemPartialUpdateDTO itemPartialUpdateDTO) {
        log.info("Запрос на частичное обновление товара id={}: name={}, cost={}",
                id, itemPartialUpdateDTO.getName(), itemPartialUpdateDTO.getCost());

        ItemResponseDTO response = itemService.partialUpdateItem(id, itemPartialUpdateDTO);

        log.info("Товар обновлён id={}: name={}, cost={}", id, response.getName(), response.getCost());
        return response;

    }

    @Operation(summary = "Удалить товар по id")
    @DeleteMapping("/item/{id}")
    public boolean deleteItem(@PathVariable("id") Long id) {
        log.warn("Запрос на удаление товара id={}", id);
        boolean deleted = itemService.deleteItem(id);
        log.info("Товар удалён: id={}, result={}", id, deleted);
        return deleted;

    }

    @Operation(summary = "Получить товар по id")
    @GetMapping("/item/{id}")
    public ItemResponseDTO getItemById(@PathVariable("id") Long id) {
        log.info("Запрос на получение товара по id={}", id);
        ItemResponseDTO response = itemService.getItemById(id);
        log.info("Товар найден id={}: name={}, cost={}", id, response.getName(), response.getCost());
        return response;

    }

    @Operation(summary = "Получить товары по параметрам")
    @ApiResponse(
            responseCode = "200",
            description = "Список товаров постранично",
            content = @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PageDTO.class)
            )
    )
    @GetMapping("/item")
    public Page<ItemResponseDTO> getItem(@RequestParam(required = false) String sortBy,
                                         @RequestParam(defaultValue = "asc") String directional,
                                         @RequestParam(required = false) String name,
                                         @RequestParam(required = false) Integer cost,
                                         @RequestParam(defaultValue = "0") @Min(0) int page,
                                         @RequestParam(defaultValue = "10") @Min(1) @Max(10) int size) {
        log.debug("Запрос на получение товаров: name={}, cost={}, sortBy={}, direction={}, page={}, size={}",
                name, cost, sortBy, directional, page, size);

        Page<ItemResponseDTO> result = itemService.getItem(sortBy, directional, name, cost, page, size);

        log.info("Найдено {} товаров (page={}, size={})", result.getTotalElements(), page, size);
        return result;

    }

}
