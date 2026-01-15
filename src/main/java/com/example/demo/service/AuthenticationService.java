package com.example.demo.service;

import com.example.demo.dto.LoginDTO.LoginRequestDto;
import com.example.demo.entity.Employee;
import com.example.demo.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final EmployeeRepository employeeRepository;
    private final JwtService jwtService;

    public String loginEmployee(LoginRequestDto req) {
        Employee employee = employeeRepository.findEmployeeByEmail(req.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Неверный логин или пароль"));

        if (!req.getPassword().equals(employee.getPassword())) {
            throw new BadCredentialsException("Неверный логин или пароль");
        }

        return jwtService.generateToken(employee.getEmail());
    }
}