package com.example.demo.dto.ClientDTO;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientPartialUpdateDTO {

    @Size(max = 64)
    private String name;

    @Size(max = 64)
    private String surname;

    @Size(max = 64)
    private String email;

    @Size(max = 64)
    private String number;
}
