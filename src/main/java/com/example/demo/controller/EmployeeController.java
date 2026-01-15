package com.example.demo.controller;

import com.example.demo.dto.EmployeeDTO.EmployeePartialUpdateDTO;
import com.example.demo.dto.EmployeeDTO.EmployeeRequestDTO;
import com.example.demo.dto.EmployeeDTO.EmployeeResponseDTO;
import com.example.demo.service.EmployeeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/employee")
    public EmployeeResponseDTO addEmployee(@Valid @RequestBody EmployeeRequestDTO employeeRequestDTO) {
        return employeeService.addEmployee(employeeRequestDTO);
    }

    @GetMapping("/employee")
    public Page<EmployeeResponseDTO> getEmployee(@RequestParam(required = false) String name,
                                                 @RequestParam(required = false) String surname,
                                                 @RequestParam(defaultValue = "0") @Min(0) int page,
                                                 @RequestParam(defaultValue = "10") @Min(1) @Max(100) int size,
                                                 @RequestParam(required = false) String sortBy,
                                                 @RequestParam(defaultValue = "asc") String direction) {
        return employeeService.getAllEmployee(name, surname, page, size, sortBy, direction);
    }

    @GetMapping("/employee/{id}")
    public EmployeeResponseDTO getEmployeeById(@PathVariable("id") Long id) {
        return employeeService.getEmployeeById(id);
    }

    @DeleteMapping("/employee/{id}")
    public boolean deleteEmployee(@PathVariable("id") Long id) {
        return employeeService.deleteEmployeeById(id);
    }

    @PatchMapping("/employee/{id}")
    public EmployeeResponseDTO partialUpdateEmployee(@PathVariable("id") Long id,
                                                     @Valid @RequestBody EmployeePartialUpdateDTO partialRequestDTO) {
        return employeeService.partialUpdateEmployee(id, partialRequestDTO);
    }
}
