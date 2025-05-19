package com.br.tasks.application.port.usecase.auth;

import com.br.tasks.domain.contract.login.AuthResponseDTO;
import com.br.tasks.domain.contract.login.LoginRequestDTO;

import javax.security.auth.login.AccountNotFoundException;

public interface LoginUseCase {
    AuthResponseDTO loginUser(LoginRequestDTO dto) throws AccountNotFoundException;
}
