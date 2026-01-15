package com.example.demo.mapper;

import com.example.demo.dto.EmployeeDTO.EmployeePartialUpdateDTO;
import com.example.demo.dto.EmployeeDTO.EmployeeRequestDTO;
import com.example.demo.dto.EmployeeDTO.EmployeeResponseDTO;
import com.example.demo.entity.Employee;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    Employee toEmployee(EmployeeRequestDTO employeeRequestDTO);

    EmployeeResponseDTO toResponseDTO(Employee employee);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void partialUpdateRequestDTO(EmployeePartialUpdateDTO employeePartialUpdateDTO, @MappingTarget Employee employee);

}
