package com.example.demo.UnitTest.Auth;

import com.example.demo.controller.AuthenticationController;
import com.example.demo.dto.LoginDTO.LoginRequestDto;
import com.example.demo.service.AuthenticationService;
import com.example.demo.service.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @InjectMocks
    private AuthenticationController authenticationController;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationService authenticationService;

    @Test
    void login_shouldReturnToken() {
        LoginRequestDto req = new LoginRequestDto();
        req.setEmail("example@mail.ru");
        req.setPassword("password123");

        when(authenticationService.loginEmployee(req)).thenReturn("token");

        ResponseEntity<String> response = authenticationController.login(req);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("token", response.getBody());
        verify(authenticationService).loginEmployee(req);
        verifyNoMoreInteractions(authenticationService);
    }
}
