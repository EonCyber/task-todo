package com.br.tasks.application.port.usecase.account;

import com.br.tasks.domain.model.AccountProfileDO;

public interface FetchAccountsUseCase {
    AccountProfileDO fetchAccountByEmail(String email);
}
