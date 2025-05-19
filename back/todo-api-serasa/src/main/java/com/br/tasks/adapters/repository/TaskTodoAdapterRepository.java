package com.br.tasks.adapters.repository;

import com.br.tasks.adapters.repository.jpa.AccountProfileJpaRepository;
import com.br.tasks.adapters.repository.jpa.TaskTodoJpaRepository;
import com.br.tasks.application.port.repository.TaskTodoRepository;
import com.br.tasks.domain.entity.AccountProfile;
import com.br.tasks.domain.entity.TaskTodo;
import com.br.tasks.domain.mapper.TaskTodoMapper;
import com.br.tasks.domain.model.TaskTodoDO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public class TaskTodoAdapterRepository implements TaskTodoRepository {
    private final TaskTodoJpaRepository orm;
    private final AccountProfileJpaRepository accountOrm;
    private final TaskTodoMapper mapper;
    public TaskTodoAdapterRepository( TaskTodoJpaRepository orm,  AccountProfileJpaRepository accountOrm, TaskTodoMapper mapper) {
        this.mapper = mapper;
        this.orm = orm;
        this.accountOrm = accountOrm;
    }
    @Override
    public Optional<TaskTodoDO> create(TaskTodoDO newTask, String email) {
        // Map entity
        TaskTodo newEntity = this.mapper.toEntity(newTask);
        // Search profile
        Optional<AccountProfile> refProfile = this.accountOrm.findByEmail(email);
        if (refProfile.isEmpty()) {
            throw new RuntimeException("Can't create task for profile unknown.");
        }
        newEntity.setProfile(refProfile.get());

        return Optional.ofNullable(this.mapper.toDomain(
                this.orm.saveAndFlush(newEntity)
        ));
    }

    @Override
    public Optional<TaskTodoDO> update(Long id, TaskTodoDO updateData) {
        Optional<TaskTodo> taskToUpdate = this.orm.findById(id);
        if (taskToUpdate.isEmpty()) {
            throw new EntityNotFoundException("Task not found");
        }
        this.mapper.updateTaskFromDo(updateData, taskToUpdate.get());
        this.orm.saveAndFlush(taskToUpdate.get());
        return Optional.ofNullable(this.mapper.toDomain(taskToUpdate.get()));
    }

    @Override
    public void deleteById(Long id) {
        this.orm.deleteById(id);
    }

    @Override
    public Page<TaskTodoDO> pageListByDate(Long userId, LocalDate date, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        LocalDateTime start = date.atStartOfDay();
        LocalDateTime end = date.atTime(LocalTime.MAX);
        Page<TaskTodo> taskTodoPageList = this.orm.findByProfileProfileIdAndToCompleteAtBetween(userId, start, end, pageable);
        // map from entity to Domain Object
        return taskTodoPageList.map(mapper::toDomain);
    }
}
