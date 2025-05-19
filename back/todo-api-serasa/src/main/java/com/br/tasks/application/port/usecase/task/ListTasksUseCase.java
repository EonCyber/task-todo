package com.br.tasks.application.port.usecase.task;

import com.br.tasks.domain.model.TaskTodoDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface ListTasksUseCase {
    Page<TaskTodoDO> listByTasksByDate(LocalDate date, Integer page, Integer size);
}
