package com.testowanieoprogramowaniaprojekt2;

import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import com.testowanieoprogramowaniaprojekt2.entities.Task;
import com.testowanieoprogramowaniaprojekt2.repositories.TaskRepository;
import com.testowanieoprogramowaniaprojekt2.services.TaskService;
import com.testowanieoprogramowaniaprojekt2.testData.TestDataBuilder;

import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class StepDefinitions {
    
    private Task task;
    private Task task1;
    private Task result;

    @Mock
    private TaskRepository taskRepository= mock(TaskRepository.class);

    @InjectMocks
    private TaskService taskService = new TaskService(taskRepository);

    @Given("everything is ok")
    public void everything_is_ok() {
        task = TestDataBuilder.exampleTask1().task();
    }

    @Given("the tasks title is empty")
    public void the_tasks_title_is_empty() {
        task = TestDataBuilder.exampleTask1().task();
        task.setTitle(null);
    }

    @Given("the tasks date has already past")
    public void the_tasks_date_has_already_past() {
    task = TestDataBuilder.exampleTask1().task();
        task.setDeadline(LocalDate.of(2010, 11,11));
    }

    @When("I add a new valid task")
    public void i_add_a_new_valid_task() {
        when(taskRepository.save(task)).thenReturn(task);
        result = taskService.createTask(task);
    }

    @When("I add a new invalid task")
    public void i_add_a_new_invalid_task() {
        when(taskRepository.save(task))
        .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Then("the task should be saved to the db")
    public void the_task_should_be_saved_to_the_db() {
        assertEquals(task, result);
    }

    @Then("saving should return an error")
    public void saving_should_return_an_error() {
        assertThrows(ResponseStatusException.class, () -> taskService.createTask(task));
    }

    @Then("not be saved to the db")
    public void not_be_saved_to_the_db() {
        verify(taskRepository, never()).save(task);
    }



    @Given("I want to delete a task")
    public void i_want_to_delete_a_task() {
        task1 = TestDataBuilder.exampleTask1().task();
    }

    @Given("the task exists in the db")
    public void the_task_exists_in_the_db() {
        taskService.createTask(task1);
        verify(taskRepository, times(1)).save(task1);
    }

    @Given("the task does not exist in the db")
    public void the_task_does_not_exist_in_the_db() {
        verify(taskRepository, never()).deleteById(task1.getId());
    }

    @When("I want to delete the existing task")
    public void i_want_to_delete_the_existing_task() {
        taskService.deleteById(task1.getId());
    }

    @When("I want to delete the nonexistant task")
    public void i_want_to_delete_the_nonexistant_task() {
        when(taskRepository.findById(task1.getId()))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @Then("it should be deleted from the db")
    public void it_should_be_deleted_from_the_db() {
        verify(taskRepository).deleteById(task1.getId());
    }

    @Then("the invalid task should not be found in the db")
    public void the_invalid_task_should_not_be_found_in_the_db() {
        assertThrows(ResponseStatusException.class, () -> taskService.getTaskById(task1.getId()));
    }

}
