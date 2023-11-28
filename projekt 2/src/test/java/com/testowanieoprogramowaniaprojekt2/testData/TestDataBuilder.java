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
                .done(false)
                .deadline(LocalDate.of(2024,11,11))
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
                .build();

        return new ExampleTask(task);
    }
    
}
