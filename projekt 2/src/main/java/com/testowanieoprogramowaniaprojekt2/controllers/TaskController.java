package com.testowanieoprogramowaniaprojekt2.controllers;

import com.testowanieoprogramowaniaprojekt2.entities.Task;
import com.testowanieoprogramowaniaprojekt2.services.TaskService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("tasks")
@Tag(name = "Tasks", description = "Task Management API")
public class TaskController {

    private final TaskService taskService;

    @GetMapping("/{id}")
    Task getById(@PathVariable Long id) {
        return taskService.getTaskById(id);
    }

    @GetMapping("/completed")
    List<Task> getCompleted() {
        return taskService.getCompletedTasks();
    }

    @GetMapping("/uncompleted")
    List<Task> getUncompleted() {
        return taskService.getUncompletedTasks();
    }

    @GetMapping("/filter/priority/{priority}")
    List<Task> getFilteredByPriority(@PathVariable int priority) {
        return taskService.getByPriority(priority);
    }
}
