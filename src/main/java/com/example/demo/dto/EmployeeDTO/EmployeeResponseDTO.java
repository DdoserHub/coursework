package com.example.demo.dto.EmployeeDTO;

import com.example.demo.enums.EmployeeRole;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeResponseDTO {
    private String name;
    private String surname;
    private String email;
    private EmployeeRole employeeRole;
}
