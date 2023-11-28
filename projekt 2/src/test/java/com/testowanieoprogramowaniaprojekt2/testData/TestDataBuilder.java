package com.testowanieoprogramowaniaprojekt2.testData;

import com.testowanieoprogramowaniaprojekt2.entities.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TestDataBuilder {

    private TestDataBuilder() {};

    public static ExampleTask exampleTask() {
        Task task = Task
                .builder()
                .id(1L)
                .title("taskTitle")
                .description("taskDescription")
                .done(false)
                .build();

        return new ExampleTask(task);
    }
    
}
