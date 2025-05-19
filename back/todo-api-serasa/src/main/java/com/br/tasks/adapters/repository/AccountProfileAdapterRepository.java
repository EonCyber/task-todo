package com.br.tasks.adapters.repository;

import com.br.tasks.adapters.repository.jpa.AccountProfileJpaRepository;
import com.br.tasks.application.port.repository.AccountProfileRepository;
import com.br.tasks.domain.entity.AccountProfile;
import com.br.tasks.domain.mapper.AccountProfileMapper;
import com.br.tasks.domain.model.AccountProfileDO;
import org.springframework.stereotype.Repository;

import javax.security.auth.login.AccountNotFoundException;
import java.util.Optional;

@Repository
public class AccountProfileAdapterRepository implements AccountProfileRepository {

    private final AccountProfileMapper mapper;
    private final AccountProfileJpaRepository orm;

    public AccountProfileAdapterRepository(AccountProfileMapper mapper, AccountProfileJpaRepository orm) {
        this.mapper = mapper;
        this.orm = orm;
    }


    @Override
    public Optional<AccountProfileDO> create(AccountProfileDO accountProfileData) {
        return Optional.ofNullable(mapper.toDomain(
                this.orm.saveAndFlush(
                        mapper.toEntity(accountProfileData))
        ));
    }

    @Override
    public Optional<AccountProfileDO> findByEmail(String email) {
        Optional<AccountProfile> profile = this.orm.findByEmail(email);
        return profile.map(mapper::toDomain);
    }
}
