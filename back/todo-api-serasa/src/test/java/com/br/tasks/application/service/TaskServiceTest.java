package com.br.tasks.application.service;

import com.br.tasks.application.port.repository.TaskTodoRepository;
import com.br.tasks.domain.contract.task.AddTaskRequestDTO;
import com.br.tasks.domain.contract.task.UpdateTaskRequestDTO;
import com.br.tasks.domain.enums.TaskStatusEnum;
import com.br.tasks.domain.enums.TaskTypeEnum;
import com.br.tasks.domain.model.TaskTodoDO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TaskServiceTest {

    private TaskService taskService;
    private TaskTodoRepository repository;
    private AuthContextService authContextService;

    @BeforeEach
    void setUp() {
        repository = mock(TaskTodoRepository.class);
        authContextService = mock(AuthContextService.class);

        taskService = new TaskService();
        taskService.repository = repository;
        taskService.authContextService = authContextService;
    }

    @Test
    void testCreateNew_success() {
        AddTaskRequestDTO dto = new AddTaskRequestDTO();
        dto.setTitle("New Task");
        dto.setStatus(TaskStatusEnum.PENDING.name());
        dto.setType(TaskTypeEnum.CHORE.name());
        dto.setToCompleteAt(LocalDateTime.now());
        TaskTodoDO savedTask = TaskTodoDO.builder()
                .id(1L)
                .title(dto.getTitle())
                .type(dto.getType())
                .status(dto.getStatus())
                .toCompleteAt(dto.getToCompleteAt())
                .build();

        when(authContextService.getAuthenticatedEmail()).thenReturn("user@example.com");
        when(repository.create(any(TaskTodoDO.class), eq("user@example.com")))
                .thenReturn(Optional.of(savedTask));

        TaskTodoDO result = taskService.createNew(dto);

        assertNotNull(result);
        assertEquals("New Task", result.getTitle());
        verify(repository, times(1)).create(any(), eq("user@example.com"));
    }

    @Test
    void testCreateNew_failure() {
        AddTaskRequestDTO dto = new AddTaskRequestDTO();
        dto.setTitle("Fail Task");

        when(authContextService.getAuthenticatedEmail()).thenReturn("user@example.com");
        when(repository.create(any(), eq("user@example.com"))).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> taskService.createNew(dto));
        assertEquals("Unable to create task", ex.getMessage());
    }

    @Test
    void testListByTasksByDate_success() {
        LocalDate date = LocalDate.now();
        Long userId = 42L;
        TaskTodoDO task = TaskTodoDO.builder().title("Test Task").build();
        Page<TaskTodoDO> page = new PageImpl<>(List.of(task));

        when(authContextService.getAuthenticatedUserId()).thenReturn(userId);
        when(repository.pageListByDate(userId, date, 0, 10)).thenReturn(page);

        Page<TaskTodoDO> result = taskService.listByTasksByDate(date, 0, 10);

        assertNotNull(result);
        assertEquals(1, result.getContent().size());
        assertEquals("Test Task", result.getContent().get(0).getTitle());
    }

    @Test
    void testUpdateById_success() {
        UpdateTaskRequestDTO dto = new UpdateTaskRequestDTO();
        dto.setTitle("Updated Title");
        dto.setStatus(TaskStatusEnum.PENDING.name());
        dto.setType("C");
        TaskTodoDO updated = TaskTodoDO.builder()
                .id(1L)
                .title("Updated Title")
                .status(TaskStatusEnum.PENDING.name())
                .build();

        when(repository.update(eq(1L), any())).thenReturn(Optional.of(updated));

        TaskTodoDO result = taskService.updateById(1L, dto);

        assertEquals("Updated Title", result.getTitle());
        assertEquals(TaskStatusEnum.PENDING.name(), result.getStatus());
    }

    @Test
    void testUpdateById_failure() {
        UpdateTaskRequestDTO dto = new UpdateTaskRequestDTO();
        dto.setTitle("Fail");

        when(repository.update(eq(1L), any())).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> taskService.updateById(1L, dto));
        assertEquals("Error updating Task.", ex.getMessage());
    }

    @Test
    void testDeleteById() {
        taskService.deleteById(99L);
        verify(repository, times(1)).deleteById(99L);
    }
}
