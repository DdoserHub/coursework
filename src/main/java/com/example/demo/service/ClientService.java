package com.example.demo.service;

import com.example.demo.dto.ClientDTO.ClientPartialUpdateDTO;
import com.example.demo.dto.ClientDTO.ClientRequestDTO;
import com.example.demo.dto.ClientDTO.ClientResponseDTO;
import com.example.demo.dto.ClientDTO.ClientWithOrdersResponseDTO;
import com.example.demo.entity.Client;
import com.example.demo.mapper.ClientMapper;
import com.example.demo.repository.ClientRepository;
import com.example.demo.specification.ClientSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    public ClientResponseDTO addClient(ClientRequestDTO clientRequestDTO) {
        Client client = clientMapper.toClient(clientRequestDTO);
        clientRepository.save(client);
        return clientMapper.toResponseDTO(client);
    }

    public ClientResponseDTO partialUpdateClient(Long id, ClientPartialUpdateDTO clientPartialUpdateDTO) {
        Client currentClient = clientRepository.getClientOrThrow(id);
        clientMapper.partialUpdateClient(clientPartialUpdateDTO, currentClient);
        clientRepository.save(currentClient);
        return clientMapper.toResponseDTO(currentClient);
    }

    public boolean deleteClient(Long id) {
        clientRepository.getClientOrThrow(id);
        clientRepository.deleteById(id);
        return true;
    }

    public Page<ClientResponseDTO> getClient(String name, String directional,
                                             String sortBy, String surname,
                                             String email, String number,
                                             int page, int size) {

        String sortField = sortBy != null && sortBy.equals("name")
                ? sortBy
                : "id";

        Sort.Direction sortDirection = directional != null && directional.equals("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortField));

        Specification<Client> specification = ClientSpecification.filterBy(name, surname, email, number);

        return clientRepository.findAll(specification, pageable).map(clientMapper::toResponseDTO);
    }

    public ClientWithOrdersResponseDTO getClientById(Long id) {
        Client client = clientRepository.getClientOrThrow(id);
        return clientMapper.toResponseWithOrderDTO(client);
    }
}
