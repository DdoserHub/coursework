package com.example.demo.controller;

import com.example.demo.dto.ItemDTO.ItemPartialUpdateDTO;
import com.example.demo.dto.ItemDTO.ItemRequestDTO;
import com.example.demo.dto.ItemDTO.ItemResponseDTO;
import com.example.demo.service.ItemService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/item")
    public ItemResponseDTO addItem(@Valid @RequestBody ItemRequestDTO itemRequestDTO) {
        return itemService.addItem(itemRequestDTO);
    }

    @PatchMapping("/item/{id}")
    public ItemResponseDTO partialUpdateItem(@PathVariable("id") Long id,
                                             @RequestBody ItemPartialUpdateDTO itemPartialUpdateDTO) {
        return itemService.partialUpdateItem(id, itemPartialUpdateDTO);
    }

    @DeleteMapping("/item/{id}")
    public boolean deleteItem(@PathVariable("id") Long id) {
        return itemService.deleteItem(id);
    }

    @GetMapping("/item/{id}")
    public ItemResponseDTO getItemById(@PathVariable("id") Long id) {
        return itemService.getItemById(id);
    }

    @GetMapping("/item")
    public Page<ItemResponseDTO> getItem(@RequestParam(required = false) String sortBy,
                                         @RequestParam(defaultValue = "asc") String directional,
                                         @RequestParam(required = false) String name,
                                         @RequestParam(required = false) Integer cost,
                                         @RequestParam(defaultValue = "0") @Min(0) int page,
                                         @RequestParam(defaultValue = "10") @Min(1) @Max(10) int size) {
        return itemService.getItem(sortBy, directional, name, cost, page, size);
    }

}
