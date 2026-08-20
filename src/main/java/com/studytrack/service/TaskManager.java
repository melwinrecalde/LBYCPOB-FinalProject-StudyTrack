package com.studytrack.service;

import com.studytrack.model.Task;
import com.studytrack.model.TaskPriority;
import com.studytrack.model.TaskStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskManager {

    private static final TaskManager INSTANCE = new TaskManager();

    private final List<Task> tasks;

    public TaskManager() {
        tasks = new ArrayList<>();
    }

    public static TaskManager getInstance() {
        return INSTANCE;
    }

    // Create
    public void addTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null.");
        }

        tasks.add(task);
    }

    // Read
    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks);
    }

    // Update
    public void updateTask(Task updatedTask) {
        if (updatedTask == null) {
            throw new IllegalArgumentException("Task cannot be null.");
        }

        int index = tasks.indexOf(updatedTask);

        if (index == -1) {
            throw new IllegalArgumentException("Task does not exist.");
        }

        tasks.set(index, updatedTask);
    }

    public void updateTask(Task oldTask, Task updatedTask) {
        if (oldTask == null || updatedTask == null) {
            throw new IllegalArgumentException("Task cannot be null.");
        }

        int index = tasks.indexOf(oldTask);

        if (index == -1) {
            throw new IllegalArgumentException("Task does not exist.");
        }

        tasks.set(index, updatedTask);
    }

    // Delete
    public void deleteTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null.");
        }

        tasks.remove(task);
    }

    // Search
    public List<Task> searchTasks(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return getAllTasks();
        }

        String searchTerm = keyword.toLowerCase();

        return tasks.stream()
                .filter(task ->
                        task.getTitle().toLowerCase().contains(searchTerm)
                                || task.getDescription().toLowerCase().contains(searchTerm)
                )
                .collect(Collectors.toList());
    }

    // Filter by status
    public List<Task> filterByStatus(TaskStatus status) {
        return tasks.stream()
                .filter(task -> task.getStatus() == status)
                .collect(Collectors.toList());
    }

    // Filter by priority
    public List<Task> filterByPriority(TaskPriority priority) {
        return tasks.stream()
                .filter(task -> task.getPriority() == priority)
                .collect(Collectors.toList());
    }

    // Upcoming tasks
    public List<Task> getUpcomingTasks() {
        LocalDate today = LocalDate.now();

        return tasks.stream()
                .filter(task ->
                        !task.isCompleted()
                                && !task.getDueDate().isBefore(today)
                )
                .collect(Collectors.toList());
    }

    // Overdue tasks
    public List<Task> getOverdueTasks() {
        return tasks.stream()
                .filter(Task::isOverdue)
                .collect(Collectors.toList());
    }

    // Completed tasks
    public List<Task> getCompletedTasks() {
        return tasks.stream()
                .filter(Task::isCompleted)
                .collect(Collectors.toList());
    }

    // Task counts for dashboard
    public int getTotalTaskCount() {
        return tasks.size();
    }

    public int getPendingTaskCount() {
        return (int) tasks.stream()
                .filter(task -> task.getStatus() == TaskStatus.PENDING)
                .count();
    }

    public int getInProgressTaskCount() {
        return (int) tasks.stream()
                .filter(task -> task.getStatus() == TaskStatus.IN_PROGRESS)
                .count();
    }

    public int getCompletedTaskCount() {
        return (int) tasks.stream()
                .filter(Task::isCompleted)
                .count();
    }

    public int getOverdueTaskCount() {
        return (int) tasks.stream()
                .filter(Task::isOverdue)
                .count();
    }
}