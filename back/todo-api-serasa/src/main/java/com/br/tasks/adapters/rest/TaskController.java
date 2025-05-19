package com.br.tasks.adapters.rest;

import com.br.tasks.application.port.rest.TaskRest;
import com.br.tasks.application.service.TaskService;
import com.br.tasks.domain.contract.task.AddTaskRequestDTO;
import com.br.tasks.domain.contract.task.UpdateTaskRequestDTO;
import com.br.tasks.domain.model.TaskTodoDO;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/task")
@Tag(name = "Tasks", description = "Endpoints for the tasks todo.")
public class TaskController implements TaskRest {
    @Autowired
    TaskService taskService;
    @Override
    public ResponseEntity<TaskTodoDO> addTask(@RequestBody() AddTaskRequestDTO request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createNew(request));
    }

    @Override
    public ResponseEntity<Page<TaskTodoDO>> getTasksByDate(
            @RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam(name = "page", defaultValue = "0") Integer page,
            @RequestParam(name = "size", defaultValue = "10") Integer size) {
        return ResponseEntity.ok(taskService.listByTasksByDate(date, page, size));
    }

    @Override
    public ResponseEntity<TaskTodoDO> updateTask(@PathVariable("id") Long id, @RequestBody() UpdateTaskRequestDTO request) {
        return ResponseEntity.ok(taskService.updateById(id, request));
    }

    @Override
    public ResponseEntity<Void> deleteTask(@PathVariable("id") Long id) {
        taskService.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
