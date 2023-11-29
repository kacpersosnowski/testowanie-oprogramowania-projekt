package com.testowanieoprogramowaniaprojekt2;

import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class StepDefinitionsTests {

    private Task task;
    private Task task1;
    private Task result;
    private List<Task> listResult;
    private List<Task> allTasks;
    private List<Task> doneTasks;
    private double statisticsResult;

    private final Long NON_EXISTENT_ID = 1000L;

    @Mock
    private TaskRepository taskRepository = mock(TaskRepository.class);

    @InjectMocks
    private TaskService taskService = new TaskService(taskRepository);

    @Given("everything is ok")
    @Given("the desired task exists in the db")
    public void everything_is_ok() {
        task = TestDataBuilder.exampleTask1().task();
    }

    @Given("the desired task does not exist in the db")
    public void task_does_not_exist_in_db() {
        task = null;
    }

    @Given("the tasks title is empty")
    public void the_tasks_title_is_empty() {
        task = TestDataBuilder.exampleTask1().task();
        task.setTitle(null);
    }

    @Given("the tasks date has already past")
    public void the_tasks_date_has_already_past() {
        task = TestDataBuilder.exampleTask1().task();
        task.setDeadline(LocalDate.of(2010, 11, 11));
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

    @When("i want to look it up")
    public void i_want_to_look_it_up() {
        if (task != null) {
            when(taskRepository.findById(task.getId()))
                    .thenReturn(Optional.ofNullable(task));
            result = taskService.getTaskById(task.getId());
        } else {
            when(taskRepository.findById(NON_EXISTENT_ID))
                    .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND));
        }
    }

    @When("i want to find it by title")
    public void i_want_to_find_it_by_title() {
        if (task != null) {
            when(taskRepository.findAll())
                    .thenReturn(List.of(task));
            listResult = taskService.getByTitle(task.getTitle().substring(2));
        } else {
            List<Task> emptyList = new ArrayList<>();
            when(taskRepository.findAll())
                    .thenReturn(emptyList);
            listResult = taskService.getByTitle("some title");
        }
    }

    @Then("the task should be saved to the db")
    @Then("it should be found")
    public void the_task_should_be_found_in_the_db() {
        assertEquals(task, result);
    }

    @Then("saving should return an error")
    public void saving_should_return_an_error() {
        assertThrows(ResponseStatusException.class, () -> taskService.createTask(task));
    }

    @Then("returning a task should return an error")
    public void returning_task_should_return_an_error() {
        assertThrows(ResponseStatusException.class, () -> taskService.getTaskById(NON_EXISTENT_ID));
    }

    @Then("it should return all tasks containing the desired title")
    public void it_should_return_all_tasks_with_title() {
        Task[] expectedResult = {task};
        assertArrayEquals(expectedResult, listResult.toArray());
    }

    @Then("it should return null")
    public void it_should_return_null() {
        assertNull(listResult);
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

    @Given("task exists in the db")
    public void there_are_tasks_in_db() {
        task = TestDataBuilder.exampleTask1().task();
    }

    @Given("is not yet completed")
    public void task_is_not_completed() {
        task.setDone(false);
    }

    @Given("is marked as completed")
    public void task_is_completed() {
        task.setDone(true);
    }

    @When("I want to mark it as done")
    public void mark_as_done() {
        when(taskRepository.findById(task.getId())).thenReturn(Optional.ofNullable(task));
        when(taskRepository.save(task)).thenReturn(task);
        result = taskService.toggle(task.getId());
    }

    @Then("its status changed to done")
    public void status_is_done() {
        assertTrue(result.isDone());
    }

    @When("I want to mark it as not complete")
    public void mark_as_not_done() {
        when(taskRepository.findById(task.getId())).thenReturn(Optional.ofNullable(task));
        when(taskRepository.save(task)).thenReturn(task);
        result = taskService.toggle(task.getId());
    }

    @Then("its status changed to not done")
    public void status_is_not_done() {
        assertFalse(result.isDone());
    }

    @Given("there are tasks in the db")
    public void tasks_in_db() {
        allTasks = TestDataBuilder.exampleTasks();
        doneTasks = TestDataBuilder.exampleDoneTasks();
    }

    @When("i want to see the statistic")
    public void get_statistics() {
        when(taskRepository.findAll()).thenReturn(allTasks);
        when(taskRepository.findAllByDoneIsTrue()).thenReturn(doneTasks);
        statisticsResult = taskService.getStatistics();
    }

    @Then("it should return it")
    public void return_statistics() {
        assertEquals(0.5, statisticsResult, 0.001);
    }

}
