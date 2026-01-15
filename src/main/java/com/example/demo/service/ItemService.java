package com.example.demo.service;

import com.example.demo.dto.ItemDTO.ItemPartialUpdateDTO;
import com.example.demo.dto.ItemDTO.ItemRequestDTO;
import com.example.demo.dto.ItemDTO.ItemResponseDTO;
import com.example.demo.entity.Item;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.ItemMapper;
import com.example.demo.repository.ItemRepository;
import com.example.demo.specification.ItemSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final ItemMapper itemMapper;

    public Item getItemOrThrow(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Товар с id: " + id + " не найден"));
    }

    public ItemResponseDTO addItem(ItemRequestDTO itemRequestDTO) {
        Item newItem = itemMapper.toItem(itemRequestDTO);
        itemRepository.save(newItem);
        return itemMapper.toItemResponseDTO(newItem);
    }

    public ItemResponseDTO partialUpdateItem(Long id, ItemPartialUpdateDTO itemPartialUpdateDTO) {
        Item currentItem = getItemOrThrow(id);
        itemMapper.partialUpdateRequestDTO(itemPartialUpdateDTO, currentItem);
        itemRepository.save(currentItem);
        return itemMapper.toItemResponseDTO(currentItem);
    }

    public boolean deleteItem(Long id) {
        getItemOrThrow(id);
        itemRepository.deleteById(id);
        return true;
    }

    public ItemResponseDTO getItemById(Long id) {
        Item currentItem = getItemOrThrow(id);
        return itemMapper.toItemResponseDTO(currentItem);
    }

    public Page<ItemResponseDTO> getItem(String sortBy,
                                         String directional, String name,
                                         Integer cost, int page, int size) {

        String sortField = sortBy != null && (sortBy.equals("name") || sortBy.equals("cost"))
                ? sortBy
                : "id";

        Sort.Direction sortDirection = directional != null && directional.equals("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortField));

        Specification<Item> specification = ItemSpecification.filterBy(name, cost);

        return itemRepository.findAll(specification, pageable).map(itemMapper::toItemResponseDTO);
    }
}
