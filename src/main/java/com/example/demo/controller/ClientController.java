package com.example.demo.controller;

import com.example.demo.dto.ClientDTO.ClientPartialUpdateDTO;
import com.example.demo.dto.ClientDTO.ClientRequestDTO;
import com.example.demo.dto.ClientDTO.ClientResponseDTO;
import com.example.demo.dto.ClientDTO.ClientWithOrdersResponseDTO;
import com.example.demo.service.ClientService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ClientController {
    private final ClientService clientService;

    @PostMapping("/client")
    public ClientResponseDTO addClient(@Valid @RequestBody ClientRequestDTO clientRequestDTO) {
        return clientService.addClient(clientRequestDTO);
    }

    @PatchMapping("/client/{id}")
    public ClientResponseDTO partialUpdateClient(@PathVariable("id") Long id,
                                                 @Valid @RequestBody ClientPartialUpdateDTO clientPartialUpdateDto) {
        return clientService.partialUpdateClient(id, clientPartialUpdateDto);
    }

    @DeleteMapping("/client/{id}")
    public boolean deleteClient(@PathVariable("id") Long id) {
        return clientService.deleteClient(id);
    }

    @GetMapping("/client")
    public Page<ClientResponseDTO> getClient(@RequestParam(required = false) String name,
                                             @RequestParam(defaultValue = "asc") String directional,
                                             @RequestParam(required = false) String sortBy,
                                             @RequestParam(required = false) String surname,
                                             @RequestParam(required = false) String email,
                                             @RequestParam(required = false) String number,
                                             @RequestParam(defaultValue = "0")@Min(0) int page,
                                             @RequestParam(defaultValue = "10")@Min(1)@Max(10) int size) {
        return clientService.getClient(name, directional, sortBy, surname, email, number, page, size);
    }

    @GetMapping("/client/{id}")
    public ClientWithOrdersResponseDTO getClientById(@PathVariable("id") Long id) {
        return clientService.getClientById(id);
    }

}
