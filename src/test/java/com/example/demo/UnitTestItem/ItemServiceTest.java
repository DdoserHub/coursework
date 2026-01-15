package com.example.demo.UnitTestItem;

import com.example.demo.dto.ItemDTO.ItemPartialUpdateDTO;
import com.example.demo.dto.ItemDTO.ItemRequestDTO;
import com.example.demo.dto.ItemDTO.ItemResponseDTO;
import com.example.demo.entity.Item;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.ItemMapper;
import com.example.demo.repository.ItemRepository;
import com.example.demo.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceTest {

    @InjectMocks
    private ItemService itemService;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private ItemMapper itemMapper;

    private ItemRequestDTO request;
    private ItemPartialUpdateDTO updateRequest;
    private ItemResponseDTO response;
    private Item item;

    @BeforeEach
    void initFixtures() {
        request = new ItemRequestDTO();
        request.setName("Phone");
        request.setDescription("desc");
        request.setCost(1000);

        updateRequest = new ItemPartialUpdateDTO();
        updateRequest.setName("NewPhone");
        updateRequest.setDescription("new desc");
        updateRequest.setCost(2000);

        response = new ItemResponseDTO();
        response.setId(1L);
        response.setName("Phone");
        response.setDescription("desc");
        response.setCost(1000);

        item = new Item();
        item.setId(1L);
        item.setName("Phone");
        item.setDescription("desc");
        item.setCost(1000);
    }

    @Test
    void addItem_shouldReturnResponseDTO() {
        when(itemMapper.toItem(request)).thenReturn(item);
        when(itemRepository.save(item)).thenReturn(item);
        when(itemMapper.toItemResponseDTO(item)).thenReturn(response);

        ItemResponseDTO result = itemService.addItem(request);

        assertEquals(response, result);
        verify(itemMapper).toItem(request);
        verify(itemRepository).save(item);
        verify(itemMapper).toItemResponseDTO(item);
    }

    @Test
    void partialUpdateItem_shouldReturnResponseDTO() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(itemRepository.save(item)).thenReturn(item);
        when(itemMapper.toItemResponseDTO(item)).thenReturn(response);

        ItemResponseDTO result = itemService.partialUpdateItem(1L, updateRequest);

        assertEquals(response, result);
        verify(itemRepository).findById(1L);
        verify(itemRepository).save(item);
        verify(itemMapper).toItemResponseDTO(item);
    }

    @Test
    void partialUpdateItem_shouldThrowNotFoundException() {
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> itemService.partialUpdateItem(1L, updateRequest));

        verify(itemRepository).findById(1L);
    }

    @Test
    void deleteItem_shouldReturnTrue() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        boolean result = itemService.deleteItem(1L);

        assertTrue(result);
        verify(itemRepository).findById(1L);
    }

    @Test
    void deleteItem_shouldThrowNotFoundException() {
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> itemService.deleteItem(1L));

        verify(itemRepository).findById(1L);
    }

    @Test
    void getItemById_shouldReturnResponseDTO() {
        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));
        when(itemMapper.toItemResponseDTO(item)).thenReturn(response);

        ItemResponseDTO result = itemService.getItemById(1L);

        assertEquals(response, result);
        verify(itemRepository).findById(1L);
        verify(itemMapper).toItemResponseDTO(item);
    }

    @Test
    void getItemById_shouldThrowNotFoundException() {
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> itemService.getItemById(1L));

        verify(itemRepository).findById(1L);
    }

    @Test
    void getItem_shouldReturnPage() {
        Page<Item> page = new PageImpl<>(List.of(item));
        when(itemRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        when(itemMapper.toItemResponseDTO(item)).thenReturn(response);

        Page<ItemResponseDTO> result = itemService.getItem(
                "name", "asc", "Phone", 1000, 0, 10
        );

        assertEquals(1, result.getTotalElements());
        assertEquals(List.of(response), result.getContent());
        verify(itemRepository).findAll(any(Specification.class), any(Pageable.class));
        verify(itemMapper).toItemResponseDTO(item);
    }
}
