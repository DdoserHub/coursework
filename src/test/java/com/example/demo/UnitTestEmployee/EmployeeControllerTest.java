package com.example.demo.UnitTestEmployee;

import com.example.demo.controller.EmployeeController;
import com.example.demo.dto.EmployeeDTO.EmployeePartialUpdateDTO;
import com.example.demo.dto.EmployeeDTO.EmployeeRequestDTO;
import com.example.demo.dto.EmployeeDTO.EmployeeResponseDTO;
import com.example.demo.enums.EmployeeRole;
import com.example.demo.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerTest {

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    private EmployeeRequestDTO request;
    private EmployeePartialUpdateDTO updateRequest;
    private EmployeeResponseDTO response;

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
    }

    @Test
    void addEmployee_shouldReturnResponseDTO() {
        when(employeeService.addEmployee(request)).thenReturn(response);

        EmployeeResponseDTO result = employeeController.addEmployee(request);

        assertEquals(response, result);
        verify(employeeService).addEmployee(request);
    }

    @Test
    void getEmployee_shouldReturnPage() {
        Page<EmployeeResponseDTO> page = new PageImpl<>(
                List.of(response),
                PageRequest.of(0, 10, Sort.by("name").ascending()),
                1
        );

        when(employeeService.getAllEmployee("Ivan", "Ivanov", 0, 10, "name", "asc"))
                .thenReturn(page);

        Page<EmployeeResponseDTO> result = employeeController.getEmployee(
                "Ivan", "Ivanov", 0, 10, "name", "asc"
        );

        assertEquals(1, result.getTotalElements());
        assertEquals(page.getContent(), result.getContent());
        verify(employeeService).getAllEmployee("Ivan", "Ivanov", 0, 10, "name", "asc");
    }

    @Test
    void getEmployeeById_shouldReturnEmployee() {
        when(employeeService.getEmployeeById(1L)).thenReturn(response);

        EmployeeResponseDTO result = employeeController.getEmployeeById(1L);

        assertEquals(response, result);
        verify(employeeService).getEmployeeById(1L);
    }

    @Test
    void deleteEmployee_shouldReturnBoolean() {
        when(employeeService.deleteEmployeeById(1L)).thenReturn(true);

        boolean result = employeeController.deleteEmployee(1L);

        assertTrue(result);
        verify(employeeService).deleteEmployeeById(1L);
    }

    @Test
    void partialUpdateEmployee_shouldReturnEmployee() {
        when(employeeService.partialUpdateEmployee(1L, updateRequest)).thenReturn(response);

        EmployeeResponseDTO result = employeeController.partialUpdateEmployee(1L, updateRequest);

        assertEquals(response, result);
        verify(employeeService).partialUpdateEmployee(1L, updateRequest);
    }
}
