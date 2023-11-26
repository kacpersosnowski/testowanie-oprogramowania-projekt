package com.testowanieoprogramowaniaprojekt2.controllers;

import com.testowanieoprogramowaniaprojekt2.entities.Task;
import com.testowanieoprogramowaniaprojekt2.services.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("tasks")
@Tag(name = "Tasks", description = "Task Management API")
public class TaskController {

    private final TaskService taskService;

    @Operation(summary = "Get Task with specifying its id.")
    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(schema = @Schema(implementation = Task.class), mediaType = "application/json")})
    @ApiResponse(
            responseCode = "404",
            content = {@Content(schema = @Schema())})
    @ApiResponse(
            responseCode = "500",
            content = {@Content(schema = @Schema())})
    @GetMapping("/{id}")
    Task getById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @Operation(summary = "Get all tasks.")
    @GetMapping
    public List<Task> getAllTasks() {
        return taskService.getAllTasks();
    }

    @Operation(summary = "Get all tasks chronologically.")
    @GetMapping("/chronologically")
    public List<Task> getAllTaskChronologically() {
        return taskService.getAllTasksChronologically();
    }

    @Operation(summary = "Get all completed tasks.")
    @GetMapping("/completed")
    List<Task> getCompleted() {
        return taskService.getCompletedTasks();
    }

    @Operation(summary = "Get all uncompleted tasks.")
    @GetMapping("/uncompleted")
    List<Task> getUncompleted() {
        return taskService.getUncompletedTasks();
    }

    @Operation(summary = "Get all tasks with provided priority.")
    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(schema = @Schema(implementation = Task.class), mediaType = "application/json")})
    @ApiResponse(
            responseCode = "400",
            content = {@Content(schema = @Schema())})
    @GetMapping("/filter/priority/{priority}")
    List<Task> getFilteredByPriority(@PathVariable int priority) {
        return taskService.getByPriority(priority);
    }

    @Operation(summary = "Create new task.")
    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(schema = @Schema(implementation = Task.class), mediaType = "application/json")})
    @ApiResponse(
            responseCode = "404",
            content = {@Content(schema = @Schema())})
    @PostMapping
    public Task createTask(@RequestBody Task task) {
        return taskService.createTask(task);
    }

    @Operation(summary = "Update existing task.")
    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(schema = @Schema(implementation = Task.class), mediaType = "application/json")})
    @ApiResponse(
            responseCode = "404",
            content = {@Content(schema = @Schema())})
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task task) {
        return taskService.updateTask(id, task);
    }


    @Operation(
            summary = "Delete Tost by id"
    )
    @DeleteMapping("/{id}")
    public void delete(
            @Parameter(description = "Task id.", example = "1")
            @PathVariable Long id
    ) {
        taskService.deleteById(id);
    }

    @Operation(
        summary = "Toggle task completion status."
        )
    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(schema = @Schema(implementation = Task.class), mediaType = "application/json")})
    @ApiResponse(
            responseCode = "404",
            content = {@Content(schema = @Schema())})
    @PatchMapping("/{id}")
    public Task toggle(@PathVariable Long id) {
        return taskService.toggle(id);
    }

    @Operation(
        summary = "Get all tasks by provided title."
        )
    @ApiResponse(
            responseCode = "200",
            content = {
                    @Content(schema = @Schema(implementation = Task.class), mediaType = "application/json")})
    @ApiResponse(
            responseCode = "400",
            content = {@Content(schema = @Schema())})
    @PostMapping("{/id}")
    List<Task> getByTitle(@RequestBody String title) {
        return taskService.getByTitle(title);
    }

}
