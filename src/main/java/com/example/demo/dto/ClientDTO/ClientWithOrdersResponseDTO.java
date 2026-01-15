package com.example.demo.dto.ClientDTO;

import com.example.demo.dto.OrderDTO.OrderWithoutClientDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ClientWithOrdersResponseDTO {
    private String name;
    private String surname;
    private String email;
    private String number;
    private List<OrderWithoutClientDTO> orders;
}
