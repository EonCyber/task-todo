package com.br.tasks.application.port.repository;

import com.br.tasks.domain.model.AccountProfileDO;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Optional;

public interface AccountProfileRepository {

    Optional<AccountProfileDO> create(AccountProfileDO accountProfileData);
    Optional<AccountProfileDO> findByEmail(String email);
}
