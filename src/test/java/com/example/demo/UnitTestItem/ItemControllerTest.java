package com.example.demo.UnitTestItem;

import com.example.demo.controller.ItemController;
import com.example.demo.dto.ItemDTO.ItemPartialUpdateDTO;
import com.example.demo.dto.ItemDTO.ItemRequestDTO;
import com.example.demo.dto.ItemDTO.ItemResponseDTO;
import com.example.demo.service.ItemService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemControllerTest {

    @InjectMocks
    private ItemController itemController;

    @Mock
    private ItemService itemService;

    private ItemRequestDTO request;
    private ItemPartialUpdateDTO updateRequest;
    private ItemResponseDTO response;

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
    }

    @Test
    void addItem_shouldReturnResponseDTO() {
        when(itemService.addItem(request)).thenReturn(response);

        ItemResponseDTO result = itemController.addItem(request);

        assertEquals(response, result);
        verify(itemService).addItem(request);
    }

    @Test
    void partialUpdateItem_shouldReturnResponseDTO() {
        when(itemService.partialUpdateItem(1L, updateRequest)).thenReturn(response);

        ItemResponseDTO result = itemController.partialUpdateItem(1L, updateRequest);

        assertEquals(response, result);
        verify(itemService).partialUpdateItem(1L, updateRequest);
    }

    @Test
    void deleteItem_shouldReturnBoolean() {
        when(itemService.deleteItem(1L)).thenReturn(true);

        boolean result = itemController.deleteItem(1L);

        assertTrue(result);
        verify(itemService).deleteItem(1L);
    }

    @Test
    void getItemById_shouldReturnResponseDTO() {
        when(itemService.getItemById(1L)).thenReturn(response);

        ItemResponseDTO result = itemController.getItemById(1L);

        assertEquals(response, result);
        verify(itemService).getItemById(1L);
    }

    @Test
    void getItem_shouldReturnPage() {
        Page<ItemResponseDTO> page = new PageImpl<>(
                List.of(response),
                PageRequest.of(0, 10),
                1
        );

        when(itemService.getItem("name", "asc", "Phone", 1000, 0, 10)).thenReturn(page);

        Page<ItemResponseDTO> result = itemController.getItem("name", "asc", "Phone", 1000, 0, 10);

        assertEquals(1, result.getTotalElements());
        assertEquals(page.getContent(), result.getContent());
        verify(itemService).getItem("name", "asc", "Phone", 1000, 0, 10);
    }
}
