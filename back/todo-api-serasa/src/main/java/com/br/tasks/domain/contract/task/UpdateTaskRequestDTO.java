package com.br.tasks.domain.contract.task;

import lombok.Data;

@Data
public class UpdateTaskRequestDTO {
    private String title;
    private String type;
    private String status;
}
