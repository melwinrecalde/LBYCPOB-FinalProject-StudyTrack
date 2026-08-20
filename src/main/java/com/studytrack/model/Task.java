package com.studytrack.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public abstract class Task implements Prioritizable {

    private String title;
    private String description;
    private LocalDate dueDate;
    private LocalTime dueTime;
    private TaskPriority priority;
    private TaskStatus status;
    private Subject subject;

    protected Task(
            String title,
            String description,
            LocalDate dueDate,
            LocalTime dueTime,
            TaskPriority priority,
            TaskStatus status,
            Subject subject
    ) {
        setTitle(title);
        setDescription(description);
        setDueDate(dueDate);
        setDueTime(dueTime);
        setPriority(priority);
        setStatus(status);
        setSubject(subject);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException(
                    "Task title cannot be empty."
            );
        }

        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        if (dueDate == null) {
            throw new IllegalArgumentException(
                    "Due date cannot be null."
            );
        }

        this.dueDate = dueDate;
    }

    public LocalTime getDueTime() {
        return dueTime;
    }

    public void setDueTime(LocalTime dueTime) {
        if (dueTime == null) {
            throw new IllegalArgumentException(
                    "Due time cannot be null."
            );
        }

        this.dueTime = dueTime;
    }

    @Override
    public TaskPriority getPriority() {
        return priority;
    }

    @Override
    public void setPriority(TaskPriority priority) {
        if (priority == null) {
            throw new IllegalArgumentException(
                    "Priority cannot be null."
            );
        }

        this.priority = priority;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        if (status == null) {
            throw new IllegalArgumentException(
                    "Status cannot be null."
            );
        }

        this.status = status;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        if (subject == null) {
            throw new IllegalArgumentException(
                    "Subject cannot be null."
            );
        }

        this.subject = subject;
    }

    public boolean isCompleted() {
        return status == TaskStatus.COMPLETED;
    }

    public boolean isOverdue() {

        if (isCompleted()) {
            return false;
        }

        LocalDateTime dueDateTime =
                LocalDateTime.of(
                        dueDate,
                        dueTime
                );

        return dueDateTime.isBefore(
                LocalDateTime.now()
        );
    }

    public abstract String getTaskType();

    @Override
    public String toString() {
        return title + " [" + getTaskType() + "]";
    }
}

