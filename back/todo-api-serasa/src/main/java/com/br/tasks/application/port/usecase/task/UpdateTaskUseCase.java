package com.br.tasks.application.port.usecase.task;

import com.br.tasks.domain.contract.task.UpdateTaskRequestDTO;
import com.br.tasks.domain.model.TaskTodoDO;

public interface UpdateTaskUseCase {
    TaskTodoDO updateById(Long id, UpdateTaskRequestDTO dto);
}
