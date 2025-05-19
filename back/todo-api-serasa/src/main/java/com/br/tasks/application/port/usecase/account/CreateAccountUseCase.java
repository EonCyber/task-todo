package com.br.tasks.application.port.usecase.account;

import com.br.tasks.domain.contract.login.AuthResponseDTO;
import com.br.tasks.domain.contract.signup.SignupRequestDTO;

public interface CreateAccountUseCase {
    AuthResponseDTO createAccountAndAuthenticate(SignupRequestDTO dto);
}
