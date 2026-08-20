package com.studytrack;

import com.studytrack.model.AssignmentTask;
import com.studytrack.model.Subject;
import com.studytrack.model.Task;
import com.studytrack.model.TaskPriority;
import com.studytrack.model.TaskStatus;
import com.studytrack.service.TaskManager;

import java.time.LocalDate;

public class TaskManagerTest {

    public static void main(String[] args) {

        Subject programming = new Subject(
                "Object-Oriented Programming",
                "LBYCPOB"
        );

        Task assignment = new AssignmentTask(
                "StudyTrack Final Project",
                "Complete the StudyTrack application.",
                LocalDate.now().plusDays(7),
                TaskPriority.HIGH,
                TaskStatus.IN_PROGRESS,
                programming
        );

        TaskManager taskManager = new TaskManager();

        taskManager.addTask(assignment);

        System.out.println("Total tasks: "
                + taskManager.getTotalTaskCount());

        System.out.println("High priority tasks: "
                + taskManager.filterByPriority(TaskPriority.HIGH).size());

        System.out.println("In-progress tasks: "
                + taskManager.getInProgressTaskCount());

        System.out.println("Upcoming tasks: "
                + taskManager.getUpcomingTasks().size());

        System.out.println("Search results: "
                + taskManager.searchTasks("StudyTrack").size());
    }
}