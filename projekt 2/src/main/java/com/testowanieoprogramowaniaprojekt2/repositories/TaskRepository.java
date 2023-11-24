package com.testowanieoprogramowaniaprojekt2.repositories;

import com.testowanieoprogramowaniaprojekt2.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findAllByDoneIsTrue();
    List<Task> findAllByDoneIsFalse();
    List<Task> findAllByPriorityIs(int priority);
}
