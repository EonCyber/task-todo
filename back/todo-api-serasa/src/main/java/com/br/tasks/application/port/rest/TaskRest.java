package com.br.tasks.application.port.rest;

import com.br.tasks.domain.contract.task.AddTaskRequestDTO;
import com.br.tasks.domain.contract.task.UpdateTaskRequestDTO;
import com.br.tasks.domain.model.TaskTodoDO;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.data.domain.Page;

import java.time.LocalDate;

public interface TaskRest {

    @PostMapping
    @Operation(
            summary = "Create a new task",
            description = "Add a new task for the authenticated user.",
            requestBody = @RequestBody(
                    required = true,
                    description = "Task creation request",
                    content = @Content(schema = @Schema(implementation = AddTaskRequestDTO.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "Task created successfully",
                            content = @Content(schema = @Schema(implementation = TaskTodoDO.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "500", description = "Server error")
            }
    )
    ResponseEntity<TaskTodoDO> addTask( AddTaskRequestDTO request);

    @GetMapping
    @Operation(
            summary = "Find Task By Date",
            description = "Finds a list of task by date for the user logged in with pagination.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Tasks Recovered",
                            content = @Content(schema = @Schema(implementation = TaskTodoDO.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "500", description = "Server error")
            }
    )
    ResponseEntity<Page<TaskTodoDO>> getTasksByDate(LocalDate date, Integer page, Integer size);

    @PutMapping("/{id}")
    @Operation(
            summary = "Update an existing task",
            description = "Partially update a task by ID for the authenticated user.",
            requestBody = @RequestBody(
                    required = true,
                    description = "Task update request (only include fields to change)",
                    content = @Content(schema = @Schema(implementation = UpdateTaskRequestDTO.class))
            ),
            parameters = {
                    @Parameter(
                            name = "id",
                            in = ParameterIn.PATH,
                            required = true,
                            description = "ID of the task to update"
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Task updated successfully",
                            content = @Content(schema = @Schema(implementation = TaskTodoDO.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "Invalid input"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "404", description = "Task not found"),
                    @ApiResponse(responseCode = "500", description = "Server error")
            }
    )
    ResponseEntity<TaskTodoDO> updateTask(Long id, UpdateTaskRequestDTO request);

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Delete a task",
            description = "Delete a task by its ID for the authenticated user.",
            parameters = {
                    @Parameter(
                            name = "id",
                            in = ParameterIn.PATH,
                            required = true,
                            description = "ID of the task to delete"
                    )
            },
            responses = {
                    @ApiResponse(responseCode = "204", description = "Task deleted successfully"),
                    @ApiResponse(responseCode = "401", description = "Unauthorized"),
                    @ApiResponse(responseCode = "404", description = "Task not found"),
                    @ApiResponse(responseCode = "500", description = "Server error")
            }
    )
    ResponseEntity<Void> deleteTask(Long id);

}
