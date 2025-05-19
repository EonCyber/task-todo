package com.br.tasks.domain.contract.signup;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignupRequestDTO {
    @NotNull
    @NotBlank
    private String fullName;

    @Email
    @NotNull
    @NotBlank
    private String email;

    @NotNull
    @NotBlank
    private String password;

    private String confirmPassword;

    private String avatarUrl;
}
