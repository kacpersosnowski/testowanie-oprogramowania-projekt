package com.testowanieoprogramowaniaprojekt2;

import io.cucumber.java.en.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.testowanieoprogramowaniaprojekt2.entities.Task;
import com.testowanieoprogramowaniaprojekt2.repositories.TaskRepository;
import com.testowanieoprogramowaniaprojekt2.services.TaskService;
import com.testowanieoprogramowaniaprojekt2.testData.TestDataBuilder;

@ExtendWith(MockitoExtension.class)
public class StepDefinitions {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    //@Mock
    private TaskService taskService;

    private Task task;
    private Task result;

    //private TaskService tks;
    //private TaskRepository tkr;

    @Given("everything is ok")
    public void everything_is_ok() {
        task = TestDataBuilder.exampleTask().task();
        //throw new io.cucumber.java.PendingException();
    }
    @When("I add a new task")
    public void i_add_a_new_task() {
        when(taskRepository.save(task)).thenReturn(task);
        //when(taskService.createTask(task).);
        result = taskService.createTask(task);
        //throw new io.cucumber.java.PendingException();
    }
    @Then("the task should be saved to the db")
    public void the_task_should_be_saved_to_the_db() {
        assertEquals(task, result);
        //throw new io.cucumber.java.PendingException();
    }



}
