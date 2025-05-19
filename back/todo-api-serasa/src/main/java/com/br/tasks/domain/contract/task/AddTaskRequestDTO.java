package com.br.tasks.domain.contract.task;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AddTaskRequestDTO {
    @NotNull
    @NotBlank
    private String title;
    @NotNull
    @NotBlank
    private String type;
    @NotNull
    @NotBlank
    private String status;
    @NotNull
    @NotBlank
    private LocalDateTime toCompleteAt;
}
