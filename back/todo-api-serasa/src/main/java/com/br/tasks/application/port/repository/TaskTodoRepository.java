package com.br.tasks.application.port.repository;

import com.br.tasks.domain.model.TaskTodoDO;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.Optional;

public interface TaskTodoRepository {
    Optional<TaskTodoDO> create(TaskTodoDO newTask, String email);
    Optional<TaskTodoDO> update(Long id, TaskTodoDO updateData);
    void deleteById(Long id);
    Page<TaskTodoDO> pageListByDate(Long userId, LocalDate date, Integer page, Integer size);
}
