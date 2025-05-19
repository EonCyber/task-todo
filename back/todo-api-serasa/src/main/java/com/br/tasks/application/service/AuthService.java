package com.br.tasks.application.service;

import com.br.tasks.application.port.repository.AccountProfileRepository;
import com.br.tasks.application.port.usecase.auth.LoginUseCase;
import com.br.tasks.application.port.usecase.auth.LogoutUseCase;
import com.br.tasks.domain.contract.account.ProfileDTO;
import com.br.tasks.domain.contract.login.AuthResponseDTO;
import com.br.tasks.domain.contract.login.LoginRequestDTO;
import com.br.tasks.domain.model.AccountProfileDO;
import com.br.tasks.util.EncodingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AuthorizationServiceException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthService implements LoginUseCase, LogoutUseCase {
    @Autowired
    AccountProfileRepository accountProfileRepository;
    @Autowired
    JwtService jwtService;

    @Override
    public AuthResponseDTO loginUser(LoginRequestDTO dto) {
        Optional<AccountProfileDO> foundProfile = accountProfileRepository.findByEmail(dto.getEmail());
        if (foundProfile.isEmpty()) {
            throw new RuntimeException("Account unknown.");
        }
        if (EncodingUtil.hashMatches(dto.getPassword(), foundProfile.get().getPassword())) {
            String token = jwtService.generateToken(dto.getEmail());
            return AuthResponseDTO.builder()
                    .accessToken(token)
                    .profile(
                            ProfileDTO.builder()
                                    .id(foundProfile.get().getId())
                                    .email(foundProfile.get().getEmail())
                                    .fullName(foundProfile.get().getFullName())
                                    .avatarUrl(foundProfile.get().getAvatarUrl())
                                    .createdAt(foundProfile.get().getCreatedAt())
                                    .build()
                    )
                    .build();
        }
        throw new AuthorizationServiceException("Password Invalid");
    }
}
