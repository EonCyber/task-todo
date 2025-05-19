package com.br.tasks.domain.model;

import com.br.tasks.domain.enums.RoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountProfileDO {
    private Long id;
    private String fullName;
    private String email;
    private String password;
    private String avatarUrl;
    private RoleEnum roleEnum;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
