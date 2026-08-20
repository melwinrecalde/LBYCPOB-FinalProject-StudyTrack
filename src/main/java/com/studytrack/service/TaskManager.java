package com.studytrack.service;

import com.studytrack.model.AssignmentTask;
import com.studytrack.model.ProjectTask;
import com.studytrack.model.QuizTask;
import com.studytrack.model.Subject;
import com.studytrack.model.Task;
import com.studytrack.model.TaskPriority;
import com.studytrack.model.TaskStatus;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TaskManager {

    private static final Path DATA_FILE =
            Paths.get("data", "tasks.dat");

    private static final TaskManager INSTANCE =
            new TaskManager();

    private final List<Task> tasks;

    public TaskManager() {

        tasks = new ArrayList<>();

        loadTasks();
    }

    public static TaskManager getInstance() {
        return INSTANCE;
    }

    // =========================
    // Create
    // =========================

    public void addTask(Task task) {

        if (task == null) {
            throw new IllegalArgumentException(
                    "Task cannot be null."
            );
        }

        tasks.add(task);

        saveTasks();
    }

    // =========================
    // Read
    // =========================

    public List<Task> getAllTasks() {

        return new ArrayList<>(tasks);
    }

    // =========================
    // Update
    // =========================

    public void updateTask(Task updatedTask) {

        if (updatedTask == null) {
            throw new IllegalArgumentException(
                    "Task cannot be null."
            );
        }

        int index =
                tasks.indexOf(updatedTask);

        if (index == -1) {
            throw new IllegalArgumentException(
                    "Task does not exist."
            );
        }

        tasks.set(index, updatedTask);

        saveTasks();
    }

    public void updateTask(
            Task oldTask,
            Task updatedTask
    ) {

        if (oldTask == null
                || updatedTask == null) {

            throw new IllegalArgumentException(
                    "Task cannot be null."
            );
        }

        int index =
                tasks.indexOf(oldTask);

        if (index == -1) {
            throw new IllegalArgumentException(
                    "Task does not exist."
            );
        }

        tasks.set(index, updatedTask);

        saveTasks();
    }

    // =========================
    // Delete
    // =========================

    public void deleteTask(Task task) {

        if (task == null) {
            throw new IllegalArgumentException(
                    "Task cannot be null."
            );
        }

        tasks.remove(task);

        saveTasks();
    }

    // =========================
    // Search
    // =========================

    public List<Task> searchTasks(
            String keyword
    ) {

        if (keyword == null
                || keyword.isBlank()) {

            return getAllTasks();
        }

        String searchTerm =
                keyword.toLowerCase();

        return tasks.stream()
                .filter(task ->
                        task.getTitle()
                                .toLowerCase()
                                .contains(searchTerm)

                                || task.getDescription()
                                .toLowerCase()
                                .contains(searchTerm)
                )
                .collect(Collectors.toList());
    }

    // =========================
    // Filter by status
    // =========================

    public List<Task> filterByStatus(
            TaskStatus status
    ) {

        return tasks.stream()
                .filter(task ->
                        task.getStatus() == status
                )
                .collect(Collectors.toList());
    }

    // =========================
    // Filter by priority
    // =========================

    public List<Task> filterByPriority(
            TaskPriority priority
    ) {

        return tasks.stream()
                .filter(task ->
                        task.getPriority() == priority
                )
                .collect(Collectors.toList());
    }

    // =========================
    // Upcoming tasks
    // =========================

    public List<Task> getUpcomingTasks() {

        LocalDate today =
                LocalDate.now();

        return tasks.stream()
                .filter(task ->
                        !task.isCompleted()
                                && !task.getDueDate()
                                .isBefore(today)
                )
                .collect(Collectors.toList());
    }

    // =========================
    // Overdue tasks
    // =========================

    public List<Task> getOverdueTasks() {

        return tasks.stream()
                .filter(Task::isOverdue)
                .collect(Collectors.toList());
    }

    // =========================
    // Completed tasks
    // =========================

    public List<Task> getCompletedTasks() {

        return tasks.stream()
                .filter(Task::isCompleted)
                .collect(Collectors.toList());
    }

    // =========================
    // Task counts
    // =========================

    public int getTotalTaskCount() {

        return tasks.size();
    }

    public int getPendingTaskCount() {

        return (int) tasks.stream()
                .filter(task ->
                        task.getStatus()
                                == TaskStatus.PENDING
                )
                .count();
    }

    public int getInProgressTaskCount() {

        return (int) tasks.stream()
                .filter(task ->
                        task.getStatus()
                                == TaskStatus.IN_PROGRESS
                )
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

    // =========================
    // Persistence
    // =========================

    private void saveTasks() {

        try {

            Files.createDirectories(
                    DATA_FILE.getParent()
            );

            try (DataOutputStream output =
                         new DataOutputStream(
                                 Files.newOutputStream(
                                         DATA_FILE
                                 )
                         )) {

                output.writeInt(tasks.size());

                for (Task task : tasks) {

                    writeTask(output, task);
                }
            }

        } catch (IOException e) {

            System.err.println(
                    "Unable to save tasks: "
                            + e.getMessage()
            );
        }
    }

    private void loadTasks() {

        if (!Files.exists(DATA_FILE)) {
            return;
        }

        try (DataInputStream input =
                     new DataInputStream(
                             Files.newInputStream(
                                     DATA_FILE
                             )
                     )) {

            int taskCount =
                    input.readInt();

            for (int i = 0;
                 i < taskCount;
                 i++) {

                Task task =
                        readTask(input);

                if (task != null) {
                    tasks.add(task);
                }
            }

        } catch (EOFException e) {

            System.err.println(
                    "Task data file is incomplete."
            );

            tasks.clear();

        } catch (IOException e) {

            System.err.println(
                    "Unable to load tasks: "
                            + e.getMessage()
            );

            tasks.clear();
        }
    }

    private void writeTask(
            DataOutputStream output,
            Task task
    ) throws IOException {

        output.writeUTF(
                task.getTaskType()
        );

        output.writeUTF(
                task.getTitle()
        );

        output.writeUTF(
                task.getDescription() == null
                        ? ""
                        : task.getDescription()
        );

        output.writeUTF(
                task.getDueDate().toString()
        );

        output.writeUTF(
                task.getPriority().name()
        );

        output.writeUTF(
                task.getStatus().name()
        );

        output.writeUTF(
                task.getSubject().getName()
        );
    }

    private Task readTask(
            DataInputStream input
    ) throws IOException {

        String taskType =
                input.readUTF();

        String title =
                input.readUTF();

        String description =
                input.readUTF();

        String dueDateString =
                input.readUTF();

        String priorityString =
                input.readUTF();

        String statusString =
                input.readUTF();

        String subjectName =
                input.readUTF();

        LocalDate dueDate =
                LocalDate.parse(
                        dueDateString
                );

        TaskPriority priority =
                TaskPriority.valueOf(
                        priorityString
                );

        TaskStatus status =
                TaskStatus.valueOf(
                        statusString
                );

        Subject subject =
                createSubject(subjectName);

        return createTask(
                taskType,
                title,
                description,
                dueDate,
                priority,
                status,
                subject
        );
    }

    private Task createTask(
            String taskType,
            String title,
            String description,
            LocalDate dueDate,
            TaskPriority priority,
            TaskStatus status,
            Subject subject
    ) {

        return switch (taskType) {

            case "Assignment" ->
                    new AssignmentTask(
                            title,
                            description,
                            dueDate,
                            priority,
                            status,
                            subject
                    );

            case "Quiz" ->
                    new QuizTask(
                            title,
                            description,
                            dueDate,
                            priority,
                            status,
                            subject
                    );

            case "Project" ->
                    new ProjectTask(
                            title,
                            description,
                            dueDate,
                            priority,
                            status,
                            subject
                    );

            default ->
                    throw new IllegalArgumentException(
                            "Invalid task type: "
                                    + taskType
                    );
        };
    }

    private Subject createSubject(
            String subjectName
    ) {

        return switch (subjectName) {

            case "Object-Oriented Programming" ->
                    new Subject(
                            "Object-Oriented Programming",
                            "LBYCPOB"
                    );

            case "Web Development" ->
                    new Subject(
                            "Web Development",
                            "WEBDEV"
                    );

            case "Database Systems" ->
                    new Subject(
                            "Database Systems",
                            "DB"
                    );

            default ->
                    throw new IllegalArgumentException(
                            "Invalid subject: "
                                    + subjectName
                    );
        };
    }
}