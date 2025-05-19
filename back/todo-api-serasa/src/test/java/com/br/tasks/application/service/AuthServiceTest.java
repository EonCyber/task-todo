package com.br.tasks.application.service;

import com.br.tasks.application.port.repository.AccountProfileRepository;
import com.br.tasks.domain.contract.login.AuthResponseDTO;
import com.br.tasks.domain.contract.login.LoginRequestDTO;
import com.br.tasks.domain.model.AccountProfileDO;
import com.br.tasks.util.EncodingUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.security.access.AuthorizationServiceException;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {

    private AuthService authService;
    private AccountProfileRepository repository;
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        repository = mock(AccountProfileRepository.class);
        jwtService = mock(JwtService.class);

        authService = new AuthService();
        authService.accountProfileRepository = repository;
        authService.jwtService = jwtService;
    }

    @Test
    void testLoginUser_success() {
        // Arrange
        LoginRequestDTO request = LoginRequestDTO.builder()
                .email("test@example.com")
                .password("123456")
                .build();

        AccountProfileDO account = AccountProfileDO.builder()
                .id(1L)
                .email(request.getEmail())
                .fullName("Test User")
                .password("hashed-password")
                .createdAt(LocalDateTime.now())
                .build();

        when(repository.findByEmail(request.getEmail()))
                .thenReturn(Optional.of(account));

        try (MockedStatic<EncodingUtil> encodingUtil = mockStatic(EncodingUtil.class)) {
            encodingUtil.when(() -> EncodingUtil.hashMatches(request.getPassword(), account.getPassword()))
                    .thenReturn(true);

            when(jwtService.generateToken(request.getEmail()))
                    .thenReturn("mock.jwt.token");

            // Act
            AuthResponseDTO response = authService.loginUser(request);

            // Assert
            assertNotNull(response);
            assertEquals("mock.jwt.token", response.getAccessToken());
            assertEquals("test@example.com", response.getProfile().getEmail());
        }
    }

    @Test
    void testLoginUser_accountNotFound() {
        LoginRequestDTO request = LoginRequestDTO.builder()
                .email("unknown@example.com")
                .password("123456")
                .build();

        when(repository.findByEmail(request.getEmail()))
                .thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> authService.loginUser(request));
        assertEquals("Account unknown.", ex.getMessage());
    }

    @Test
    void testLoginUser_invalidPassword() {
        LoginRequestDTO request = LoginRequestDTO.builder()
                .email("test@example.com")
                .password("wrong")
                .build();

        AccountProfileDO account = AccountProfileDO.builder()
                .email(request.getEmail())
                .password("hashed-password")
                .build();

        when(repository.findByEmail(request.getEmail()))
                .thenReturn(Optional.of(account));

        try (MockedStatic<EncodingUtil> encodingUtil = mockStatic(EncodingUtil.class)) {
            encodingUtil.when(() -> EncodingUtil.hashMatches(request.getPassword(), account.getPassword()))
                    .thenReturn(false);

            AuthorizationServiceException ex = assertThrows(AuthorizationServiceException.class,
                    () -> authService.loginUser(request));

            assertEquals("Password Invalid", ex.getMessage());
        }
    }
}
