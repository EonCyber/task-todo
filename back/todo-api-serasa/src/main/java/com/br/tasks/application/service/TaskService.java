package com.br.tasks.application.service;

import com.br.tasks.application.port.repository.TaskTodoRepository;
import com.br.tasks.application.port.usecase.task.CreateTaskUseCase;
import com.br.tasks.application.port.usecase.task.DeleteTaskUseCase;
import com.br.tasks.application.port.usecase.task.ListTasksUseCase;
import com.br.tasks.application.port.usecase.task.UpdateTaskUseCase;
import com.br.tasks.domain.contract.task.AddTaskRequestDTO;
import com.br.tasks.domain.contract.task.UpdateTaskRequestDTO;
import com.br.tasks.domain.model.TaskTodoDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class TaskService implements CreateTaskUseCase, ListTasksUseCase, UpdateTaskUseCase, DeleteTaskUseCase {

    @Autowired
    TaskTodoRepository repository;
    @Autowired
    AuthContextService authContextService;
    @Override
    public TaskTodoDO createNew(AddTaskRequestDTO dto) {
        // validate dto

        // map dto to DO
        TaskTodoDO newTaskModel = TaskTodoDO.builder()
                .title(dto.getTitle())
                .type(dto.getType())
                .status(dto.getStatus())
                .toCompleteAt(dto.getToCompleteAt())
                .build();
        // persist
        Optional<TaskTodoDO> taskTodoCreated = repository.create(newTaskModel, authContextService.getAuthenticatedEmail());
        if (taskTodoCreated.isEmpty()) {
            throw new RuntimeException("Unable to create task");
        }
        return taskTodoCreated.get();
    }

    @Override
    public Page<TaskTodoDO> listByTasksByDate(LocalDate date, Integer page, Integer size) {
        //    TODO    Validate data

        return repository.pageListByDate(authContextService.getAuthenticatedUserId(),date, page, size);
    }

    @Override
    public TaskTodoDO updateById(Long id, UpdateTaskRequestDTO dto) {
        TaskTodoDO taskTodoDO = TaskTodoDO.builder()
                .type(dto.getType() != null ? dto.getType() : null)
                .status(dto.getStatus() != null ? dto.getStatus() : null)
                .title(dto.getTitle() != null ? dto.getTitle() : null)
                .build();
        Optional<TaskTodoDO> update = repository.update(id, taskTodoDO);
        if (update.isEmpty()) {
            throw new RuntimeException("Error updating Task.");
        }
        return update.get();
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
