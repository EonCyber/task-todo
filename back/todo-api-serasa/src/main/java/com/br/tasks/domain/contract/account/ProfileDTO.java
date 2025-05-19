package com.br.tasks.domain.contract.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {
    private Long id;
    private String fullName;
    private String email;
    private String avatarUrl;
    private LocalDateTime createdAt;
}
