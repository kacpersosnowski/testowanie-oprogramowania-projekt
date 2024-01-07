package com.testowanieoprogramowaniaprojekt2.testData;

import com.testowanieoprogramowaniaprojekt2.entities.Task;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TestDataBuilder {

    private TestDataBuilder() {
    }

    ;

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

    public static List<Task> exampleTasks() {
        Task task1 = Task.builder()
                .id(1L)
                .title("some title")
                .description("some description")
                .done(false)
                .priority(1)
                .deadline(LocalDate.of(2025, 11, 11))
                .build();
        Task task2 = Task.builder()
                .id(2L)
                .title("some title2")
                .description("some description2")
                .done(false)
                .priority(1)
                .deadline(LocalDate.of(2025, 11, 12))
                .build();
        Task task3 = Task.builder()
                .id(3L)
                .title("some title3")
                .description("some description3")
                .done(true)
                .priority(2)
                .deadline(LocalDate.of(2025, 11, 13))
                .build();
        Task task4 = Task.builder()
                .id(4L)
                .title("some title4")
                .description("some description4")
                .done(true)
                .priority(3)
                .deadline(LocalDate.of(2025, 11, 14))
                .build();
        Task task5 = Task.builder()
                .id(5L)
                .title("some title5")
                .description("some description5")
                .done(true)
                .priority(3)
                .deadline(LocalDate.of(2025, 5, 14))
                .build();

        return new ArrayList<>(List.of(task1, task2, task3, task4, task5));
    }

    public static List<Task> exampleDoneTasks() {
        Task task3 = Task.builder()
                .id(3L)
                .title("some title3")
                .description("some description3")
                .done(true)
                .deadline(LocalDate.of(2025, 11, 13))
                .build();
        Task task4 = Task.builder()
                .id(4L)
                .title("some title4")
                .description("some description4")
                .done(true)
                .deadline(LocalDate.of(2025, 11, 14))
                .build();

        return new ArrayList<>(List.of(task3, task4));
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
