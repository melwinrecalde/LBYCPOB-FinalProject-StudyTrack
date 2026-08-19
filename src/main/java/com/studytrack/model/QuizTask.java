package com.studytrack.model;

import java.time.LocalDate;

public class QuizTask extends Task {

    public QuizTask(
            String title,
            String description,
            LocalDate dueDate,
            TaskPriority priority,
            TaskStatus status,
            Subject subject
    ) {
        super(title, description, dueDate, priority, status, subject);
    }

    @Override
    public String getTaskType() {
        return "Quiz";
    }
}