package com.testowanieoprogramowaniaprojekt2.testData;

import com.testowanieoprogramowaniaprojekt2.entities.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TestDataBuilder {

    private TestDataBuilder() {};

    public static ExampleTask exampleTask1() {
        Task task = Task
                .builder()
                .id(1L)
                .title("taskTitle")
                .description("taskDescription")
                .done(true)
                .deadline(LocalDate.of(2024,11,11))
                .priority(1)
                .build();

        return new ExampleTask(task);
    }

    public static ExampleTask exampleTask2() {
        Task task = Task
                .builder()
                .id(2L)
                .title("taskTitle2")
                .description("taskDescription")
                .done(false)
                .deadline(LocalDate.of(2025,11,11))
                .priority(3)
                .build();

        return new ExampleTask(task);
    }

    public static ExampleTask exampleTask3() {
        Task task = Task
                .builder()
                .id(2L)
                .title("taskTitle2")
                .description("taskDescription")
                .done(false)
                .deadline(LocalDate.of(2026,11,11))
                .priority(3)
                .build();

        return new ExampleTask(task);
    }

    public static ExampleTaskList exampleTaskList() {
        List<Task> taskList = new ArrayList<>();
        taskList.add(exampleTask1().task());
        taskList.add(exampleTask2().task());
        taskList.add(exampleTask3().task());
        return new ExampleTaskList(taskList);
    }

}
