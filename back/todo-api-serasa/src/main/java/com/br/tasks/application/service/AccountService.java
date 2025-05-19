package com.br.tasks.application.service;

import com.br.tasks.application.port.repository.AccountProfileRepository;
import com.br.tasks.application.port.usecase.account.CreateAccountUseCase;
import com.br.tasks.application.port.usecase.account.FetchAccountsUseCase;
import com.br.tasks.domain.contract.account.ProfileDTO;
import com.br.tasks.domain.contract.login.AuthResponseDTO;
import com.br.tasks.domain.contract.signup.SignupRequestDTO;
import com.br.tasks.domain.enums.RoleEnum;
import com.br.tasks.domain.model.AccountProfileDO;
import com.br.tasks.util.EncodingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AccountService implements CreateAccountUseCase, FetchAccountsUseCase {
    @Autowired
    AccountProfileRepository repository;
    @Autowired
    JwtService jwtService;
    /**
     * @param dto SignupRequestDTO
     * @return AuthResponseDTO
     */
    @Override
    public AuthResponseDTO createAccountAndAuthenticate(SignupRequestDTO dto) {
        // validate dto

        // map dto to model,
        AccountProfileDO newProfileData = AccountProfileDO.builder()
                .email(dto.getEmail())
                .roleEnum(RoleEnum.USER)
                .password(EncodingUtil.hash(dto.getPassword()))
                .fullName(dto.getFullName())
                .avatarUrl(dto.getAvatarUrl())
                .build();
        // pass model to repository
        Optional<AccountProfileDO> created = repository.create(newProfileData);
        if (created.isEmpty()) {
            throw new RuntimeException("Error creating account");
        }
        // create jwt
        String token = jwtService.generateToken(created.get().getEmail());

        return AuthResponseDTO.builder()
                .profile(
                        ProfileDTO.builder()
                                .id(created.get().getId())
                                .fullName(created.get().getFullName())
                                .email(created.get().getEmail())
                                .avatarUrl(created.get().getAvatarUrl())
                                .createdAt(created.get().getCreatedAt())
                                .build()
                )
                .accessToken(token)
                .build();
    }

    @Override
    public AccountProfileDO fetchAccountByEmail(String email) {
        Optional<AccountProfileDO> profileFound = repository.findByEmail(email);
        if(profileFound.isEmpty()) {
            throw new RuntimeException("Account not found.");
        }
        return profileFound.get();
    }
}
