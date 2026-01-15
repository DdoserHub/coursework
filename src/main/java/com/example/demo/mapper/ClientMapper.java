package com.example.demo.mapper;

import com.example.demo.dto.ClientDTO.ClientPartialUpdateDTO;
import com.example.demo.dto.ClientDTO.ClientRequestDTO;
import com.example.demo.dto.ClientDTO.ClientResponseDTO;
import com.example.demo.dto.ClientDTO.ClientWithOrdersResponseDTO;
import com.example.demo.entity.Client;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    Client toClient(ClientRequestDTO clientRequestDTO);

    ClientResponseDTO toResponseDTO(Client client);

    ClientWithOrdersResponseDTO toResponseWithOrderDTO(Client client);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdateClient(ClientPartialUpdateDTO clientPartialUpdateDTO, @MappingTarget Client client);

}
