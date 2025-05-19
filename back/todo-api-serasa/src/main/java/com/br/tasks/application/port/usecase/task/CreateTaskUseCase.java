package com.br.tasks.application.port.usecase.task;

import com.br.tasks.domain.contract.task.AddTaskRequestDTO;
import com.br.tasks.domain.model.TaskTodoDO;

public interface CreateTaskUseCase {

    TaskTodoDO createNew(AddTaskRequestDTO dto);
}
