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
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;

public class AddTaskController {

    @FXML
    private TextField titleField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private DatePicker dueDatePicker;

    @FXML
    private ComboBox<String> taskTypeComboBox;

    @FXML
    private ComboBox<String> priorityComboBox;

    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    private ComboBox<String> subjectComboBox;

    private final TaskManager taskManager =
            TaskManager.getInstance();

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

        taskTypeComboBox.getSelectionModel().selectFirst();
        priorityComboBox.getSelectionModel().select("Medium");
        statusComboBox.getSelectionModel().select("Pending");
        subjectComboBox.getSelectionModel().selectFirst();
    }

    @FXML
    private void handleCreateTask(ActionEvent event) {

        try {

            String title =
                    titleField.getText().trim();

            String description =
                    descriptionArea.getText().trim();

            LocalDate dueDate =
                    dueDatePicker.getValue();

            if (title.isEmpty()) {
                showError("Task title is required.");
                return;
            }

            if (dueDate == null) {
                showError("Due date is required.");
                return;
            }

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

            Task task =
                    createTask(
                            taskTypeComboBox.getValue(),
                            title,
                            description,
                            dueDate,
                            priority,
                            status,
                            subject
                    );

            taskManager.addTask(task);

            showSuccess();

            clearForm();

        } catch (Exception e) {

            showError(e.getMessage());
        }
    }

    @FXML
    private void handleCancel(ActionEvent event)
            throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/dashboard.fxml")
        );

        Scene scene = new Scene(
                loader.load(),
                800,
                600
        );

        Stage stage = (Stage) titleField
                .getScene()
                .getWindow();

        stage.setScene(scene);
        stage.setTitle("StudyTrack - Dashboard");
        stage.show();
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

    private void clearForm() {

        titleField.clear();

        descriptionArea.clear();

        dueDatePicker.setValue(null);

        taskTypeComboBox
                .getSelectionModel()
                .selectFirst();

        priorityComboBox
                .getSelectionModel()
                .select("Medium");

        statusComboBox
                .getSelectionModel()
                .select("Pending");

        subjectComboBox
                .getSelectionModel()
                .selectFirst();
    }

    private void showSuccess() {

        Alert alert =
                new Alert(
                        Alert.AlertType.INFORMATION
                );

        alert.setTitle("StudyTrack");

        alert.setHeaderText(
                "Task Created"
        );

        alert.setContentText(
                "The academic task was successfully added."
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

        alert.setTitle("StudyTrack");

        alert.setHeaderText(
                "Unable to Create Task"
        );

        alert.setContentText(message);

        alert.showAndWait();
    }
}