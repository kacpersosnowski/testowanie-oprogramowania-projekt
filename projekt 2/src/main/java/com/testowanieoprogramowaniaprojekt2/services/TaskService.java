package com.testowanieoprogramowaniaprojekt2.services;

import com.testowanieoprogramowaniaprojekt2.entities.Task;
import com.testowanieoprogramowaniaprojekt2.repositories.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    private static final int MAX_PRIORITY = 5;

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found."));
    }

    public List<Task> getUncompletedTasks() {
        return taskRepository.findAllByDoneIsFalse();
    }

    public List<Task> getCompletedTasks() {
        return taskRepository.findAllByDoneIsTrue();
    }

    public List<Task> getByPriority(int priority) {
        if(priority > MAX_PRIORITY || priority < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Priority is out of range");
        }

        return taskRepository.findAllByPriorityIs(priority);
    }
}
