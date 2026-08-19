package com.studytrack.model;

import java.time.LocalDate;

public class AssignmentTask extends Task {

    public AssignmentTask(
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
        return "Assignment";
    }
}