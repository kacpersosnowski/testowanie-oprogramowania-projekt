package com.testowanieoprogramowaniaprojekt2;

import com.testowanieoprogramowaniaprojekt2.controllers.TaskController;
import com.testowanieoprogramowaniaprojekt2.entities.Task;
import com.testowanieoprogramowaniaprojekt2.services.TaskService;
import com.testowanieoprogramowaniaprojekt2.testData.TestDataBuilder;
import org.junit.jupiter.api.Assertions;
import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 2, time = 1)
@Measurement(iterations = 20, time = 1)
@Fork(value = 1)
public class TaskBenchmark {

    @State(Scope.Benchmark)
    public static class BenchmarkState {
        ConfigurableApplicationContext context;
        TaskController taskController;
        TaskService taskService;
        ArrayList<Task> tasks;
        Task newTask;
        Task updatedTask1;
        Task updatedTask2;
        Task updatedTask3;

        @Setup(Level.Trial)
        public synchronized void initialize() {
            context = SpringApplication.run(Application.class);
            taskController = context.getBean(TaskController.class);
            taskService = context.getBean(TaskService.class);

            for (Task task : TestDataBuilder.exampleTasks()) {
                taskController.createTask(task);
            }
            tasks = new ArrayList<>(taskController.getAllTasks());

            newTask = Task.builder()
                    .title("taskTitle")
                    .description("taskDescription")
                    .done(true)
                    .deadline(LocalDate.of(2024, 11, 11))
                    .priority(1)
                    .build();
            updatedTask1 = TestDataBuilder.exampleTasks().get(0);
            updatedTask1.setTitle("New title");
            updatedTask2 = TestDataBuilder.exampleTasks().get(0);
            updatedTask2.setDescription("New description");
            updatedTask3 = TestDataBuilder.exampleTasks().get(0);
            updatedTask3.setDeadline(LocalDate.of(2050, 10, 10));
        }

        @TearDown(Level.Trial)
        public synchronized void tearDown() {
            List<Task> tasksToDelete = new ArrayList<>(taskController.getAllTasks());
            for (Task task : tasksToDelete) {
                taskController.delete(task.getId());
            }

            context.close();
        }
    }

    @Benchmark
    public static void getByIdBenchmark(BenchmarkState state, Blackhole blackhole) {
        Long taskId = state.tasks.get(0).getId();
        blackhole.consume(state.taskController.getById(taskId));
    }

    @Benchmark
    public static void getById_Fail(BenchmarkState state, Blackhole blackhole) {
        Long taskId = 0L;
        blackhole.consume(
                Assertions.assertThrows(ResponseStatusException.class,
                        () -> state.taskController.getById(taskId))
        );
    }

    @Benchmark
    public static void getAllTasksBenchmark(BenchmarkState state, Blackhole blackhole) {
        blackhole.consume(state.taskController.getAllTasks());
    }

    @Benchmark
    public static void getAllTaskChronologicallyBenchmark(BenchmarkState state, Blackhole blackhole) {
        blackhole.consume(state.taskController.getAllTaskChronologically());
    }

    @Benchmark
    public static void getCompletedBenchmark(BenchmarkState state, Blackhole blackhole) {
        blackhole.consume(state.taskController.getCompleted());
    }

    @Benchmark
    public static void getUncompletedBenchmark(BenchmarkState state, Blackhole blackhole) {
        blackhole.consume(state.taskController.getUncompleted());
    }

    @Benchmark
    public static void getFilteredByPriorityBenchmark(BenchmarkState state, Blackhole blackhole) {
        int priority = 1;
        blackhole.consume(state.taskController.getFilteredByPriority(priority));
    }

    @Benchmark
    public static void createTaskBenchmark(BenchmarkState state, Blackhole blackhole) {
        blackhole.consume(state.taskController.createTask(state.newTask));
    }

    @Benchmark
    public static void updateTaskTitleBenchmark(BenchmarkState state, Blackhole blackhole) {
        Long taskId = state.tasks.get(1).getId();
        blackhole.consume(state.taskController.updateTask(taskId, state.updatedTask1));
    }

    @Benchmark
    public static void updateTaskDescriptionBenchmark(BenchmarkState state, Blackhole blackhole) {
        Long taskId = state.tasks.get(2).getId();
        blackhole.consume(state.taskController.updateTask(taskId, state.updatedTask2));
    }

    @Benchmark
    public static void updateTaskDateBenchmark(BenchmarkState state, Blackhole blackhole) {
        Long taskId = state.tasks.get(3).getId();
        blackhole.consume(state.taskController.updateTask(taskId, state.updatedTask3));
    }

    @Benchmark
    public static void deleteBenchmark(BenchmarkState state) {
        Long taskIdToDelete = state.tasks.get(4).getId();
        state.taskController.delete(taskIdToDelete);
    }

    @Benchmark
    public static void toggleBenchmark(BenchmarkState state, Blackhole blackhole) {
        Long taskIdToToggle = state.tasks.get(0).getId();
        blackhole.consume(state.taskController.toggle(taskIdToToggle));
    }

    @Benchmark
    public static void getByTitleBenchmarkAll(BenchmarkState state, Blackhole blackhole) {
        String titleToSearch = "some";
        blackhole.consume(state.taskController.getByTitle(titleToSearch));
    }

    @Benchmark
    public static void getByTitleBenchmarkNone(BenchmarkState state, Blackhole blackhole) {
        String titleToSearch = "none";
        blackhole.consume(state.taskController.getByTitle(titleToSearch));
    }

    @Benchmark
    public static void getStatisticsBenchmarkNone(BenchmarkState state, Blackhole blackhole) {
        blackhole.consume(state.taskController.getStatistics());
    }

    @Benchmark
    public static void getSummaryBenchmarkNone(BenchmarkState state, Blackhole blackhole) {
        blackhole.consume(state.taskController.getSummary());
    }

    @Benchmark
    public static void getAllTasksServiceBenchmark(BenchmarkState state, Blackhole blackhole) {
        blackhole.consume(state.taskService.getAllTasks());
    }

    @Benchmark
    public static void getByIdServiceBenchmark(BenchmarkState state, Blackhole blackhole) {
        Long taskId = state.tasks.get(0).getId();
        blackhole.consume(state.taskService.getTaskById(taskId));
    }

    @Benchmark
    public static void updateTaskTitleServiceBenchmark(BenchmarkState state, Blackhole blackhole) {
        Long taskId = state.tasks.get(1).getId();
        blackhole.consume(state.taskService.updateTask(taskId, state.updatedTask1));
    }

    @Benchmark
    public static void createTaskServiceBenchmark(BenchmarkState state, Blackhole blackhole) {
        blackhole.consume(state.taskService.createTask(state.newTask));
    }

    @Benchmark
    public static void getAllTaskChronologicallyServiceBenchmark(BenchmarkState state, Blackhole blackhole) {
        blackhole.consume(state.taskService.getAllTasksChronologically());
    }

    @Benchmark
    public static void getUncompletedServiceBenchmark(BenchmarkState state, Blackhole blackhole) {
        blackhole.consume(state.taskService.getUncompletedTasks());
    }

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }

    @SpringBootApplication
    public static class Application {
        public static void main(String[] args) {
            SpringApplication.run(Application.class, args);
        }
    }
}
