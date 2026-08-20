package com.studytrack.service;

import com.studytrack.model.AssignmentTask;
import com.studytrack.model.Subject;
import com.studytrack.model.Task;
import com.studytrack.model.TaskPriority;
import com.studytrack.model.TaskStatus;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {

    @Test
    void addTask_shouldIncreaseTaskCount() {

        TaskManager manager = new TaskManager();

        int initialCount =
                manager.getTotalTaskCount();

        Subject subject =
                new Subject(
                        "Object-Oriented Programming",
                        "LBYCPOB"
                );

        Task task =
                new AssignmentTask(
                        "OOP Assignment",
                        "Complete the OOP assignment",
                        LocalDate.now().plusDays(7),
                        LocalTime.of(23, 59),
                        TaskPriority.HIGH,
                        TaskStatus.PENDING,
                        subject
                );

        manager.addTask(task);

        assertEquals(
                initialCount + 1,
                manager.getTotalTaskCount()
        );
    }

    @Test
    void filterByStatus_shouldReturnOnlyMatchingTasks() {

        TaskManager manager = new TaskManager();

        Subject subject =
                new Subject(
                        "Object-Oriented Programming",
                        "LBYCPOB"
                );

        Task pendingTask =
                new AssignmentTask(
                        "Pending Task",
                        "Test pending task",
                        LocalDate.now().plusDays(5),
                        LocalTime.of(23, 59),
                        TaskPriority.MEDIUM,
                        TaskStatus.PENDING,
                        subject
                );

        Task completedTask =
                new AssignmentTask(
                        "Completed Task",
                        "Test completed task",
                        LocalDate.now().plusDays(5),
                        LocalTime.of(23, 59),
                        TaskPriority.MEDIUM,
                        TaskStatus.COMPLETED,
                        subject
                );

        manager.addTask(pendingTask);
        manager.addTask(completedTask);

        List<Task> results =
                manager.filterByStatus(
                        TaskStatus.PENDING
                );

        assertTrue(
                results.contains(pendingTask)
        );

        assertFalse(
                results.contains(completedTask)
        );
    }

    @Test
    void searchTasks_shouldFindTaskByTitle() {

        TaskManager manager = new TaskManager();

        Subject subject =
                new Subject(
                        "Object-Oriented Programming",
                        "LBYCPOB"
                );

        Task task =
                new AssignmentTask(
                        "Java Assignment",
                        "Practice Java programming",
                        LocalDate.now().plusDays(5),
                        LocalTime.of(23, 59),
                        TaskPriority.HIGH,
                        TaskStatus.PENDING,
                        subject
                );

        manager.addTask(task);

        List<Task> results =
                manager.searchTasks("Java");

        assertTrue(
                results.contains(task)
        );
    }
}