package com.br.tasks.application.service;

import com.br.tasks.application.port.repository.AccountProfileRepository;
import com.br.tasks.domain.contract.login.AuthResponseDTO;
import com.br.tasks.domain.contract.signup.SignupRequestDTO;
import com.br.tasks.domain.enums.RoleEnum;
import com.br.tasks.domain.model.AccountProfileDO;
import com.br.tasks.util.EncodingUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AccountServiceTest {

    private AccountService accountService;
    private AccountProfileRepository repository;
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        repository = mock(AccountProfileRepository.class);
        jwtService = mock(JwtService.class);

        accountService = new AccountService();
        accountService.repository = repository;
        accountService.jwtService = jwtService;
    }

    @Test
    @DisplayName("Account Service - Test CreateAccountAndAuthenticate Success")
    void testCreateAccountAndAuthenticate_success() {
        // Arrange
        SignupRequestDTO dto = new SignupRequestDTO();
        dto.setEmail("test@example.com");
        dto.setPassword("123456");
        dto.setFullName("Test User");
        dto.setAvatarUrl("https://avatar.com");

        AccountProfileDO savedAccount = AccountProfileDO.builder()
                .id(1L)
                .email(dto.getEmail())
                .fullName(dto.getFullName())
                .avatarUrl(dto.getAvatarUrl())
                .password(EncodingUtil.hash(dto.getPassword()))
                .roleEnum(RoleEnum.USER)
                .build();

        when(repository.create(any())).thenReturn(Optional.of(savedAccount));
        when(jwtService.generateToken(savedAccount.getEmail())).thenReturn("mocked.jwt.token");

        // Act
        AuthResponseDTO response = accountService.createAccountAndAuthenticate(dto);

        // Assert
        assertNotNull(response);
        assertEquals(dto.getEmail(), response.getProfile().getEmail());
        assertEquals("mocked.jwt.token", response.getAccessToken());
    }

    @Test
    @DisplayName("Account Service - Test fetchAccountByEmail Success")
    void testFetchAccountByEmail_found() {
        String email = "test@example.com";
        AccountProfileDO profile = AccountProfileDO.builder()
                .email(email)
                .fullName("Test")
                .build();

        when(repository.findByEmail(email)).thenReturn(Optional.of(profile));

        AccountProfileDO result = accountService.fetchAccountByEmail(email);
        assertEquals("test@example.com", result.getEmail());
    }

    @Test
    @DisplayName("Account Service - Test fetchAccountByEmail Not Found")
    void testFetchAccountByEmail_notFound() {
        when(repository.findByEmail("missing@example.com")).thenReturn(Optional.empty());
        RuntimeException ex = assertThrows(RuntimeException.class, () ->
                accountService.fetchAccountByEmail("missing@example.com"));
        assertEquals("Account not found.", ex.getMessage());
    }
}
