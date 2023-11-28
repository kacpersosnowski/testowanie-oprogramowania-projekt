package com.testowanieoprogramowaniaprojekt2.services;

import com.testowanieoprogramowaniaprojekt2.entities.Task;
import com.testowanieoprogramowaniaprojekt2.repositories.TaskRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    private static final int MAX_PRIORITY = 5;
    private static final int MIN_PRIORITY = 0;

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found."));
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getAllTasksChronologically() {
        return taskRepository.findByOrderByDeadlineAsc();
    }

    public List<Task> getUncompletedTasks() {
        return taskRepository.findAllByDoneIsFalse();
    }

    public List<Task> getCompletedTasks() {
        return taskRepository.findAllByDoneIsTrue();
    }

    public List<Task> getByPriority(int priority) {
        if (priority > MAX_PRIORITY || priority < MIN_PRIORITY) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Priority is out of range");
        }

        return taskRepository.findAllByPriorityIs(priority);
    }

    public Task createTask(Task task) {
        if (task.getTitle() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title cannot be null");
        }
        if (verifyDate(task) == false) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Task date is past");
        }
        return taskRepository.save(task);
    }

    private boolean verifyDate(Task task) {
        if (LocalDate.now().isAfter(task.getDeadline())) {
            return false;
        }
        return true;
    }

    public Task updateTask(Long id, Task task) {
        if (taskRepository.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found task with id: " + id);
        }
        task.setId(id);
        return taskRepository.save(task);
    }

    public void deleteById(Long id) {
        taskRepository.findById(id);
        taskRepository.deleteById(id);
    }

    public Task toggle(Long id) {
        return taskRepository.findById(id)
            .map(foundTask -> {
                foundTask.setDone(!foundTask.isDone());
                return taskRepository.save(foundTask);
            })
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found in db."));
    }

    public List<Task> getByTitle(String title) {
        List<Task> foundTasks = new ArrayList<Task>();
        List<Task> allTasks = taskRepository.findAll();
        for (Task t : allTasks) {
            if (t.getTitle().toLowerCase().contains(title.toLowerCase())) {
                foundTasks.add(t);
            }
        }
        if (foundTasks.isEmpty()){
            return null;
        } else {
            return foundTasks;
        }
    }
}
