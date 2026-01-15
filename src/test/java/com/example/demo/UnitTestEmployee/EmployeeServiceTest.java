package com.example.demo.UnitTestEmployee;

import com.example.demo.dto.EmployeeDTO.EmployeePartialUpdateDTO;
import com.example.demo.dto.EmployeeDTO.EmployeeRequestDTO;
import com.example.demo.dto.EmployeeDTO.EmployeeResponseDTO;
import com.example.demo.entity.Employee;
import com.example.demo.enums.EmployeeRole;
import com.example.demo.exception.NotFoundException;
import com.example.demo.mapper.EmployeeMapper;
import com.example.demo.repository.EmployeeRepository;
import com.example.demo.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @InjectMocks
    private EmployeeService employeeService;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeMapper employeeMapper;

    private EmployeeRequestDTO request;
    private EmployeePartialUpdateDTO updateRequest;
    private EmployeeResponseDTO response;
    private Employee employee;

    @BeforeEach
    void initFixtures() {
        request = new EmployeeRequestDTO();
        request.setName("Ivan");
        request.setSurname("Ivanov");
        request.setEmail("ivan@example.com");
        request.setPassword("password123");
        request.setEmployeeRole(EmployeeRole.ADMINISTRATOR);

        updateRequest = new EmployeePartialUpdateDTO();
        updateRequest.setName("NewName");
        updateRequest.setSurname("NewSurname");

        response = new EmployeeResponseDTO();
        response.setId(1L);
        response.setName("Ivan");
        response.setSurname("Ivanov");
        response.setEmail("ivan@example.com");
        response.setEmployeeRole(EmployeeRole.ADMINISTRATOR);

        employee = new Employee();
        employee.setId(1L);
        employee.setName("Ivan");
        employee.setSurname("Ivanov");
        employee.setEmail("ivan@example.com");
        employee.setPassword("hashed");
        employee.setEmployeeRole(EmployeeRole.ADMINISTRATOR);
    }

    @Test
    void addEmployee_shouldReturnResponseDTO() {
        when(employeeMapper.toEmployee(request)).thenReturn(employee);
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeMapper.toResponseDTO(employee)).thenReturn(response);

        EmployeeResponseDTO result = employeeService.addEmployee(request);

        assertEquals(response, result);
        verify(employeeMapper).toEmployee(request);
        verify(employeeRepository).save(employee);
        verify(employeeMapper).toResponseDTO(employee);
    }

    @Test
    void partialUpdateEmployee_shouldReturnResponseDTO() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeRepository.save(employee)).thenReturn(employee);
        when(employeeMapper.toResponseDTO(employee)).thenReturn(response);

        EmployeeResponseDTO result = employeeService.partialUpdateEmployee(1L, updateRequest);

        assertEquals(response, result);
        verify(employeeRepository).findById(1L);
        verify(employeeRepository).save(employee);
        verify(employeeMapper).toResponseDTO(employee);
    }

    @Test
    void partialUpdateEmployee_shouldThrowNotFoundException() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> employeeService.partialUpdateEmployee(1L, updateRequest));

        verify(employeeRepository).findById(1L);
    }

    @Test
    void deleteEmployeeById_shouldReturnTrue() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));

        boolean result = employeeService.deleteEmployeeById(1L);

        assertTrue(result);
        verify(employeeRepository).findById(1L);
    }

    @Test
    void deleteEmployeeById_shouldThrowNotFoundException() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> employeeService.deleteEmployeeById(1L));

        verify(employeeRepository).findById(1L);
    }

    @Test
    void getAllEmployee_shouldReturnPage() {
        Page<Employee> page = new PageImpl<>(List.of(employee));
        when(employeeRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(page);
        when(employeeMapper.toResponseDTO(employee)).thenReturn(response);

        Page<EmployeeResponseDTO> result = employeeService.getAllEmployee(
                "Ivan", "Ivanov", 0, 10, "name", "asc"
        );

        assertEquals(1, result.getTotalElements());
        assertEquals(List.of(response), result.getContent());
        verify(employeeRepository).findAll(any(Specification.class), any(Pageable.class));
        verify(employeeMapper).toResponseDTO(employee);
    }

    @Test
    void getEmployeeById_shouldReturnEmployee() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.of(employee));
        when(employeeMapper.toResponseDTO(employee)).thenReturn(response);

        EmployeeResponseDTO result = employeeService.getEmployeeById(1L);

        assertEquals(response, result);
        verify(employeeRepository).findById(1L);
        verify(employeeMapper).toResponseDTO(employee);
    }

    @Test
    void getEmployeeById_shouldThrowNotFoundException() {
        when(employeeRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class,
                () -> employeeService.getEmployeeById(1L));

        verify(employeeRepository).findById(1L);
    }
}
