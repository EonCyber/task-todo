package com.br.tasks.domain.contract.login;

import com.br.tasks.domain.contract.account.ProfileDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseDTO {
    private ProfileDTO profile;
    private String accessToken;
}
