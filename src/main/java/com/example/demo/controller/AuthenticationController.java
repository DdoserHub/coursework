package com.example.demo.controller;

import com.example.demo.dto.LoginDTO.LoginRequestDto;
import com.example.demo.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Войти в систему и получить JWT токен")
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequestDto req) {
        log.info("Запрос на login: username={}", req.getEmail());

        String token = authenticationService.loginEmployee(req);

        log.info("Успешный login: username={}", req.getEmail());
        return ResponseEntity.ok(token);
    }
}
