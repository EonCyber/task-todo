package com.br.tasks.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskTodoDO {
    private Long id;

    private String title;

    private String type;

    private String status;

    private Long profileId;

    private LocalDateTime createdAt;

    private LocalDateTime toCompleteAt;

    private LocalDateTime completedAt;
}
