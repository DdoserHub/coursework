package com.example.demo.UnitTestClient;

import com.example.demo.dto.ClientDTO.ClientPartialUpdateDTO;
import com.example.demo.dto.ClientDTO.ClientRequestDTO;
import com.example.demo.dto.ClientDTO.ClientResponseDTO;
import com.example.demo.dto.ClientDTO.ClientWithOrdersResponseDTO;
import com.example.demo.entity.Client;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.ClientMapper;
import com.example.demo.repository.ClientRepository;
import com.example.demo.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ClientServiceTest {

    @InjectMocks
    private ClientService clientService;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientMapper clientMapper;

    private ClientRequestDTO request;
    private ClientResponseDTO response;
    private ClientPartialUpdateDTO requestUpdate;
    private Client client;
    private NotFoundException notFoundException;

    @BeforeEach
    public void initFixtures() {
        this.client = new Client(
                1L, "Ivan",
                "Ivanov", "example@mail.ru",
                "79005553535", null);
        this.request = new ClientRequestDTO(
                "Ivan", "Ivanov",
                "example@mail.ru", "79005553535");
        this.response = new ClientResponseDTO(
                1L,"Ivan", "Ivanov",
                "example@mail.ru", "79005553535");
        this.requestUpdate = new ClientPartialUpdateDTO(
                "Ivan", "Ivanov",
                "example@mail.ru", "79005553535");

        this.notFoundException = new NotFoundException("Клиент с id: 1 не найден");
    }

    @Test
    public void addClient_shouldReturnClientResponseDTO() {
        when(clientMapper.toClient(request)).thenReturn(client);
        when(clientMapper.toResponseDTO(client)).thenReturn(response);

        ClientResponseDTO result = clientService.addClient(request);

        assertEquals(response, result);
        verify(clientMapper).toClient(request);
        verify(clientRepository).save(client);
        verify(clientMapper).toResponseDTO(client);
    }

    @Test
    public void partialUpdateClient_shouldReturnClientResponseDTO() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(clientMapper.toResponseDTO(client)).thenReturn(response);

        ClientResponseDTO result = clientService.partialUpdateClient(1L, requestUpdate);

        assertEquals(response, result);
        verify(clientRepository).findById(1L);
        verify(clientRepository).save(client);
        verify(clientMapper).toResponseDTO(client);
    }

    @Test
    public void partialUpdateClient_shouldThrowNotFoundException() {
        when(clientRepository.findById(1L)).thenThrow(notFoundException);

        NotFoundException result = assertThrows(NotFoundException.class,
                () -> clientService.partialUpdateClient(1L, requestUpdate));

        assertEquals(result.getMessage(), notFoundException.getMessage());
        verify(clientRepository).findById(1L);
    }

    @Test
    public void deleteClient_shouldReturnBoolean() {
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));

        boolean result = clientService.deleteClient(1L);

        assertTrue(result);
        verify(clientRepository).findById(1L);
    }

    @Test
    public void deleteClient_shouldThrowNotFoundException() {
        when(clientRepository.findById(1L)).thenThrow(notFoundException);

        NotFoundException result = assertThrows(NotFoundException.class,
                () -> clientService.deleteClient(1L));

        assertEquals(result.getMessage(), notFoundException.getMessage());
        verify(clientRepository).findById(1L);
    }

    @Test
    public void getClient_shouldReturnPageClientResponseDTO() {
        List<ClientResponseDTO> responseList = List.of(response);
        var pageClientResponseDTO = new PageImpl<>(
                responseList);
        Page<Client> pageClient = new PageImpl<>(List.of(client));
        when(clientRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenReturn(pageClient);
        when(clientMapper.toResponseDTO(client)).thenReturn(response);

        Page<ClientResponseDTO> result = clientService.getClient(
                "Ivan", "ASC",
                "name", "Ivanov",
                "some@mail.ru", "79005553535",
                0, 10);

        assertEquals(responseList, result.getContent());
        assertEquals(responseList.size(), result.getTotalElements());
        verify(clientRepository).findAll(any(Specification.class), any(Pageable.class));
        verify(clientMapper).toResponseDTO(client);
    }

    @Test
    public void getClientById_shouldReturnClientWithOrdersResponseDTO() {
        var responseWithOrders = new ClientWithOrdersResponseDTO(
                1L,"Ivan", "Ivanov",
                "example@mail.ru", "79005553535",
                null);
        when(clientRepository.findById(1L)).thenReturn(Optional.of(client));
        when(clientMapper.toResponseWithOrderDTO(client)).thenReturn(responseWithOrders);

        ClientWithOrdersResponseDTO result = clientService.getClientById(1L);

        assertEquals(responseWithOrders, result);
        verify(clientRepository).findById(1L);
        verify(clientMapper).toResponseWithOrderDTO(client);
    }

    @Test
    public void getClientById_shouldThrowNotFoundException() {
        when(clientRepository.findById(1L)).thenReturn(Optional.empty());

        NotFoundException result = assertThrows(NotFoundException.class,
                () -> clientService.getClientById(1L));


        assertEquals(notFoundException.getMessage(), result.getMessage());
        verify(clientRepository).findById(1L);
    }

}
