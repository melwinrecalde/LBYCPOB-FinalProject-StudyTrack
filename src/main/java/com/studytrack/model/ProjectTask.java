package com.studytrack.model;

import java.time.LocalDate;
import java.time.LocalTime;

public class ProjectTask extends Task {

    public ProjectTask(
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
        return "Project";
    }
}