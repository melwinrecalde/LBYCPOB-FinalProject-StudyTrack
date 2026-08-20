package com.studytrack.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class AssignmentTask extends Task {

    public AssignmentTask(
            String title,
            String description,
            LocalDate dueDate,
            LocalTime dueTime,
            TaskPriority priority,
            TaskStatus status,
            Subject subject
    ) {
        super(
                title,
                description,
                dueDate,
                dueTime,
                priority,
                status,
                subject
        );
    }

    @Override
    public String getTaskType() {
        return "Assignment";
    }
}