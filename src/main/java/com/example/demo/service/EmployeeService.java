package com.example.demo.service;

import com.example.demo.dto.EmployeeDTO.EmployeePartialUpdateDTO;
import com.example.demo.dto.EmployeeDTO.EmployeeRequestDTO;
import com.example.demo.dto.EmployeeDTO.EmployeeResponseDTO;
import com.example.demo.entity.Employee;

import com.example.demo.mapper.EmployeeMapper;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.specification.EmployeeSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeMapper employeeMapper;
    private final EmployeeRepository employeeRepository;

    public EmployeeResponseDTO addEmployee(EmployeeRequestDTO employeeRequestDTO) {
        Employee newEmployee = employeeMapper.toEmployee(employeeRequestDTO);
        employeeRepository.save(newEmployee);
        return employeeMapper.toResponseDTO(newEmployee);
    }

    public Page<EmployeeResponseDTO> getAllEmployee(String name, String surname,
                                                    int page, int size,
                                                    String sortBy, String direction) {

        String sortField = sortBy != null && (sortBy.equals("name") || sortBy.equals("surname"))
                ? sortBy
                : "id";

        Sort.Direction sortDirection = direction != null && direction.equals("desc")
                ? Sort.Direction.DESC
                : Sort.Direction.ASC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortField));

        Specification<Employee> specification = EmployeeSpecification.filterBy(name, surname);

        return employeeRepository.findAll(specification, pageable)
                .map(employeeMapper::toResponseDTO);

    }

    public EmployeeResponseDTO getEmployeeById(long id) {
        Employee currentEmployee = employeeRepository.getEmployeeOrThrow(id);
        return employeeMapper.toResponseDTO(currentEmployee);
    }

    public boolean deleteEmployeeById(Long id) {
        employeeRepository.getEmployeeOrThrow(id);
        employeeRepository.deleteById(id);
        return true;
    }

    public EmployeeResponseDTO partialUpdateEmployee(Long id, EmployeePartialUpdateDTO employeePartialUpdateDTO) {
        Employee currentEmployee = employeeRepository.getEmployeeOrThrow(id);
        employeeMapper.partialUpdateRequestDTO(employeePartialUpdateDTO, currentEmployee);
        employeeRepository.save(currentEmployee);
        return employeeMapper.toResponseDTO(currentEmployee);
    }
}
