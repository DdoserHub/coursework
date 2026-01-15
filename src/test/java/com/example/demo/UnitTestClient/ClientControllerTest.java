package com.example.demo.UnitTestClient;


import com.example.demo.controller.ClientController;
import com.example.demo.dto.ClientDTO.ClientPartialUpdateDTO;
import com.example.demo.dto.ClientDTO.ClientRequestDTO;
import com.example.demo.dto.ClientDTO.ClientResponseDTO;
import com.example.demo.dto.ClientDTO.ClientWithOrdersResponseDTO;
import com.example.demo.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(MockitoExtension.class)
public class ClientControllerTest {

    @InjectMocks
    private ClientController clientController;

    @Mock
    private ClientService clientService;

    private ClientRequestDTO request;
    private ClientResponseDTO response;
    private ClientPartialUpdateDTO requestUpdate;

    @BeforeEach
    public void initFixtures() {
        this.request = new ClientRequestDTO(
                "Ivan", "Ivanov",
                "example@mail.ru", "79005553535");
        this.response = new ClientResponseDTO(
                1L, "Ivan", "Ivanov",
                "example@mail.ru", "79005553535");
        this.requestUpdate = new ClientPartialUpdateDTO(
                "Ivan", "Ivanov",
                "example@mail.ru", "79005553535");
    }

    @Test
    public void addClient_shouldReturnResponseDTO() {
        when(clientService.addClient(request)).thenReturn(response);

        ClientResponseDTO result = clientController.addClient(request);

        assertEquals(response, result);
        verify(clientService).addClient(request);
    }

    @Test
    public void partialUpdateClient_shouldReturnResponseDTO() {
        when(clientService.partialUpdateClient(1L, requestUpdate)).thenReturn(response);

        ClientResponseDTO result = clientController.partialUpdateClient(1L, requestUpdate);

        assertEquals(response, result);
        verify(clientService).partialUpdateClient(1L, requestUpdate);
    }

    @Test
    public void deleteClient_shouldReturnBoolean() {
        when(clientService.deleteClient(1L)).thenReturn(true);
        boolean result = clientController.deleteClient(1L);

        assertTrue(result);
        verify(clientService).deleteClient(1L);
    }

    @Test
    public void getClient_shouldReturnPageClientResponseDTO() {
        List<ClientResponseDTO> responseList = List.of(response);
        var pageClientResponseDTO = new PageImpl<>(
                responseList,
                PageRequest.of(0, 10),
                responseList.size());
        when(clientService.getClient(
                "Ivan", "ASC",
                "name", "Ivanov",
                "some@mail.ru", "79005553535",
                0, 10)).thenReturn(pageClientResponseDTO);

        Page<ClientResponseDTO> result = clientController.getClient(
                "Ivan", "ASC",
                "name", "Ivanov",
                "some@mail.ru", "79005553535",
                0, 10);

        assertEquals(pageClientResponseDTO.getContent(), result.getContent());
        assertEquals(pageClientResponseDTO.getTotalElements(), result.getTotalElements());
        verify(clientService).getClient(
                "Ivan", "ASC",
                "name", "Ivanov",
                "some@mail.ru", "79005553535",
                0, 10);
    }

    @Test
    public void getClientById_shouldReturnClientWithOrdersResponseDTO() {
        var responseWithOrders = new ClientWithOrdersResponseDTO(
                1L, "Ivan", "Ivanov",
                "example@mail.ru", "79005553535",
                null);
        when(clientService.getClientById(1L)).thenReturn(responseWithOrders);

        ClientWithOrdersResponseDTO result = clientController.getClientById(1L);

        assertEquals(responseWithOrders, result);
        verify(clientService).getClientById(1L);
    }
}
