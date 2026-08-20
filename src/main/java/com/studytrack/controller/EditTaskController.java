package com.studytrack.controller;

import com.studytrack.model.AssignmentTask;
import com.studytrack.model.ProjectTask;
import com.studytrack.model.QuizTask;
import com.studytrack.model.Subject;
import com.studytrack.model.Task;
import com.studytrack.model.TaskPriority;
import com.studytrack.model.TaskStatus;
import com.studytrack.service.TaskManager;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class EditTaskController {

    @FXML
    private TextField titleField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private DatePicker dueDatePicker;

    @FXML
    private TextField dueTimeField;

    @FXML
    private ComboBox<String> taskTypeComboBox;

    @FXML
    private ComboBox<String> priorityComboBox;

    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    private ComboBox<String> subjectComboBox;

    @FXML
    private Button cancelButton;

    private final TaskManager taskManager =
            TaskManager.getInstance();

    private static final DateTimeFormatter TIME_FORMAT =
            DateTimeFormatter.ofPattern("HH:mm");

    private Task originalTask;

    @FXML
    private void initialize() {

        taskTypeComboBox.getItems().addAll(
                "Assignment",
                "Quiz",
                "Project"
        );

        priorityComboBox.getItems().addAll(
                "Low",
                "Medium",
                "High"
        );

        statusComboBox.getItems().addAll(
                "Pending",
                "In Progress",
                "Completed"
        );

        subjectComboBox.getItems().addAll(
                "Object-Oriented Programming",
                "Web Development",
                "Database Systems"
        );

        cancelButton.setOnAction(
                this::handleCancel
        );
    }

    public void setTask(Task task) {

        this.originalTask = task;

        titleField.setText(
                task.getTitle()
        );

        descriptionArea.setText(
                task.getDescription()
        );

        dueDatePicker.setValue(
                task.getDueDate()
        );

        dueTimeField.setText(
                task.getDueTime().format(
                        TIME_FORMAT
                )
        );

        taskTypeComboBox.setValue(
                task.getTaskType()
        );

        priorityComboBox.setValue(
                formatPriority(
                        task.getPriority()
                )
        );

        statusComboBox.setValue(
                formatStatus(
                        task.getStatus()
                )
        );

        subjectComboBox.setValue(
                task.getSubject().getName()
        );
    }

    @FXML
    private void handleSaveChanges(
            ActionEvent event
    ) {

        if (originalTask == null) {

            showError(
                    "No task selected."
            );

            return;
        }

        String title =
                titleField.getText().trim();

        String description =
                descriptionArea.getText().trim();

        LocalDate dueDate =
                dueDatePicker.getValue();

        if (title.isEmpty()) {

            showError(
                    "Task title is required."
            );

            return;
        }

        if (dueDate == null) {

            showError(
                    "Due date is required."
            );

            return;
        }

        LocalTime dueTime;

        try {

            dueTime =
                    LocalTime.parse(
                            dueTimeField.getText().trim(),
                            TIME_FORMAT
                    );

        } catch (DateTimeParseException e) {

            showError(
                    "Due time must use HH:mm format, "
                            + "for example 23:59."
            );

            return;
        }

        if (taskTypeComboBox.getValue() == null) {

            showError(
                    "Task type is required."
            );

            return;
        }

        if (priorityComboBox.getValue() == null) {

            showError(
                    "Priority is required."
            );

            return;
        }

        if (statusComboBox.getValue() == null) {

            showError(
                    "Status is required."
            );

            return;
        }

        if (subjectComboBox.getValue() == null) {

            showError(
                    "Subject is required."
            );

            return;
        }

        try {

            TaskPriority priority =
                    convertPriority(
                            priorityComboBox.getValue()
                    );

            TaskStatus status =
                    convertStatus(
                            statusComboBox.getValue()
                    );

            Subject subject =
                    createSubject(
                            subjectComboBox.getValue()
                    );

            Task updatedTask =
                    createTask(
                            taskTypeComboBox.getValue(),
                            title,
                            description,
                            dueDate,
                            dueTime,
                            priority,
                            status,
                            subject
                    );

            taskManager.updateTask(
                    originalTask,
                    updatedTask
            );

            showSuccess();

            closeWindow(event);

        } catch (IllegalArgumentException e) {

            showError(
                    e.getMessage()
            );
        }
    }

    private void handleCancel(
            ActionEvent event
    ) {

        closeWindow(event);
    }

    private void closeWindow(
            ActionEvent event
    ) {

        Node source =
                (Node) event.getSource();

        Stage stage =
                (Stage) source
                        .getScene()
                        .getWindow();

        stage.close();
    }

    private Task createTask(
            String taskType,
            String title,
            String description,
            LocalDate dueDate,
            LocalTime dueTime,
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
                            dueTime,
                            priority,
                            status,
                            subject
                    );

            case "Quiz" ->
                    new QuizTask(
                            title,
                            description,
                            dueDate,
                            dueTime,
                            priority,
                            status,
                            subject
                    );

            case "Project" ->
                    new ProjectTask(
                            title,
                            description,
                            dueDate,
                            dueTime,
                            priority,
                            status,
                            subject
                    );

            default ->
                    throw new IllegalArgumentException(
                            "Invalid task type."
                    );
        };
    }

    private TaskPriority convertPriority(
            String priority
    ) {

        return switch (priority) {

            case "Low" ->
                    TaskPriority.LOW;

            case "Medium" ->
                    TaskPriority.MEDIUM;

            case "High" ->
                    TaskPriority.HIGH;

            default ->
                    throw new IllegalArgumentException(
                            "Invalid priority."
                    );
        };
    }

    private TaskStatus convertStatus(
            String status
    ) {

        return switch (status) {

            case "Pending" ->
                    TaskStatus.PENDING;

            case "In Progress" ->
                    TaskStatus.IN_PROGRESS;

            case "Completed" ->
                    TaskStatus.COMPLETED;

            default ->
                    throw new IllegalArgumentException(
                            "Invalid status."
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
                            "Invalid subject."
                    );
        };
    }

    private String formatPriority(
            TaskPriority priority
    ) {

        return switch (priority) {

            case LOW -> "Low";

            case MEDIUM -> "Medium";

            case HIGH -> "High";
        };
    }

    private String formatStatus(
            TaskStatus status
    ) {

        return switch (status) {

            case PENDING -> "Pending";

            case IN_PROGRESS -> "In Progress";

            case COMPLETED -> "Completed";
        };
    }

    private void showSuccess() {

        Alert alert =
                new Alert(
                        Alert.AlertType.INFORMATION
                );

        alert.setTitle(
                "StudyTrack"
        );

        alert.setHeaderText(
                "Task Updated"
        );

        alert.setContentText(
                "The task was successfully updated."
        );

        alert.showAndWait();
    }

    private void showError(
            String message
    ) {

        Alert alert =
                new Alert(
                        Alert.AlertType.ERROR
                );

        alert.setTitle(
                "StudyTrack"
        );

        alert.setHeaderText(
                "Unable to Update Task"
        );

        alert.setContentText(
                message
        );

        alert.showAndWait();
    }
}