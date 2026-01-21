package com.example.demo.controller;

import com.example.demo.dto.AuthenticationDTO.EmployeeResponseLoginDTO;
import com.example.demo.entity.Employee;
import com.example.demo.enums.EmployeeRole;
import com.example.demo.repository.EmployeeRepository;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AdminPanelController {

    private final EmployeeRepository employeeRepository;

    @Operation(summary = "Добавить работника через админ панель")
    @PostMapping("/create/employee")
    public ResponseEntity<EmployeeResponseLoginDTO> createTestEmployee() {

        Employee saved = employeeRepository.save(new Employee(
                999999999L,
                "ivan",
                "ivanov",
                "ivan@example.com",
                "password123",
                EmployeeRole.ADMINISTRATOR
        ));

        EmployeeResponseLoginDTO response = new EmployeeResponseLoginDTO(
                saved.getId(),
                saved.getName(),
                saved.getSurname(),
                saved.getEmail(),
                saved.getPassword(),
                saved.getEmployeeRole()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

