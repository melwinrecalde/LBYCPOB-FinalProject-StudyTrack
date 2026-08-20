package com.studytrack.controller;

import com.studytrack.model.Task;
import com.studytrack.model.TaskPriority;
import com.studytrack.model.TaskStatus;
import com.studytrack.service.TaskManager;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public class DashboardController {

    @FXML
    private ListView<String> taskListView;

    @FXML
    private TextField searchField;

    @FXML
    private ComboBox<String> statusFilterComboBox;

    @FXML
    private ComboBox<String> priorityFilterComboBox;

    private final TaskManager taskManager =
            TaskManager.getInstance();

    @FXML
    private void initialize() {

        statusFilterComboBox.getItems().addAll(
                "Pending",
                "In Progress",
                "Completed"
        );

        priorityFilterComboBox.getItems().addAll(
                "Low",
                "Medium",
                "High"
        );

        statusFilterComboBox.setOnAction(
                event -> refreshTaskList()
        );

        priorityFilterComboBox.setOnAction(
                event -> refreshTaskList()
        );

        refreshTaskList();
    }
    private void refreshTaskList() {

        List<Task> tasks =
                taskManager.getAllTasks();

        String keyword =
                searchField.getText();

        if (keyword != null && !keyword.isBlank()) {
            tasks =
                    taskManager.searchTasks(keyword);
        }

        String status =
                statusFilterComboBox.getValue();

        if (status != null) {

            TaskStatus taskStatus =
                    convertStatus(status);

            tasks =
                    tasks.stream()
                            .filter(task ->
                                    task.getStatus() == taskStatus
                            )
                            .toList();
        }

        String priority =
                priorityFilterComboBox.getValue();

        if (priority != null) {

            TaskPriority taskPriority =
                    convertPriority(priority);

            tasks =
                    tasks.stream()
                            .filter(task ->
                                    task.getPriority() == taskPriority
                            )
                            .toList();
        }

        taskListView.setItems(
                FXCollections.observableArrayList(
                        tasks.stream()
                                .map(this::formatTask)
                                .toList()
                )
        );
    }

    private String formatTask(Task task) {

        return task.getTitle()
                + " | "
                + task.getTaskType()
                + "\nDescription: "
                + task.getDescription()
                + "\nDue: "
                + task.getDueDate()
                + " | Priority: "
                + task.getPriority()
                + " | Status: "
                + task.getStatus();
    }

    @FXML
    private void handleSearch(ActionEvent event) {

        refreshTaskList();
    }

    @FXML
    private void handleClearFilters(ActionEvent event) {

        searchField.clear();

        statusFilterComboBox
                .getSelectionModel()
                .clearSelection();

        priorityFilterComboBox
                .getSelectionModel()
                .clearSelection();

        refreshTaskList();
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

    @FXML
    private void handleAddTask(
            ActionEvent event
    ) throws IOException {

        FXMLLoader loader =
                new FXMLLoader(
                        getClass().getResource(
                                "/fxml/add-task.fxml"
                        )
                );

        Scene scene =
                new Scene(
                        loader.load(),
                        800,
                        600
                );

        Stage stage =
                (Stage) ((Node) event.getSource())
                        .getScene()
                        .getWindow();

        stage.setScene(scene);

        stage.setTitle(
                "StudyTrack - Add Task"
        );

        stage.show();
    }

    @FXML
    private void handleEditTask()
            throws IOException {

        int selectedIndex =
                taskListView
                        .getSelectionModel()
                        .getSelectedIndex();

        if (selectedIndex == -1) {

            showError(
                    "Please select a task to edit."
            );

            return;
        }

        List<Task> tasks =
                getFilteredTasks();

        if (selectedIndex >= tasks.size()) {

            showError(
                    "Unable to find selected task."
            );

            return;
        }

        Task selectedTask =
                tasks.get(selectedIndex);

        FXMLLoader loader =
                new FXMLLoader(
                        getClass().getResource(
                                "/fxml/edit-task.fxml"
                        )
                );

        Scene scene =
                new Scene(
                        loader.load(),
                        800,
                        600
                );

        EditTaskController controller =
                loader.getController();

        controller.setTask(selectedTask);

        Stage stage =
                new Stage();

        stage.setTitle(
                "StudyTrack - Edit Task"
        );

        stage.setScene(scene);

        stage.initModality(
                Modality.APPLICATION_MODAL
        );

        stage.showAndWait();

        refreshTaskList();
    }

    @FXML
    private void handleDeleteTask() {

        int selectedIndex =
                taskListView
                        .getSelectionModel()
                        .getSelectedIndex();

        if (selectedIndex == -1) {

            showError(
                    "Please select a task to delete."
            );

            return;
        }

        List<Task> tasks =
                getFilteredTasks();

        if (selectedIndex >= tasks.size()) {

            showError(
                    "Unable to find selected task."
            );

            return;
        }

        Task selectedTask =
                tasks.get(selectedIndex);

        Alert confirmation =
                new Alert(
                        Alert.AlertType.CONFIRMATION
                );

        confirmation.setTitle(
                "StudyTrack"
        );

        confirmation.setHeaderText(
                "Delete Task"
        );

        confirmation.setContentText(
                "Are you sure you want to delete \""
                        + selectedTask.getTitle()
                        + "\"?"
        );

        Optional<ButtonType> result =
                confirmation.showAndWait();

        if (result.isPresent()
                && result.get() == ButtonType.OK) {

            taskManager.deleteTask(
                    selectedTask
            );

            refreshTaskList();
        }
    }

    private List<Task> getFilteredTasks() {

        List<Task> tasks =
                taskManager.getAllTasks();

        String keyword =
                searchField.getText();

        if (keyword != null
                && !keyword.isBlank()) {

            tasks =
                    taskManager.searchTasks(
                            keyword
                    );
        }

        String status =
                statusFilterComboBox.getValue();

        if (status != null) {

            TaskStatus taskStatus =
                    convertStatus(status);

            tasks =
                    tasks.stream()
                            .filter(task ->
                                    task.getStatus()
                                            == taskStatus
                            )
                            .toList();
        }

        String priority =
                priorityFilterComboBox.getValue();

        if (priority != null) {

            TaskPriority taskPriority =
                    convertPriority(priority);

            tasks =
                    tasks.stream()
                            .filter(task ->
                                    task.getPriority()
                                            == taskPriority
                            )
                            .toList();
        }

        return tasks;
    }

    @FXML
    private void handleStartStudying(
            ActionEvent event
    ) throws IOException {

        FXMLLoader loader =
                new FXMLLoader(
                        getClass().getResource(
                                "/fxml/study-session.fxml"
                        )
                );

        Scene scene =
                new Scene(
                        loader.load(),
                        800,
                        600
                );

        Stage stage =
                (Stage) ((Node) event.getSource())
                        .getScene()
                        .getWindow();

        stage.setScene(scene);

        stage.setTitle(
                "StudyTrack - Study Session"
        );

        stage.show();
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
                "Action Failed"
        );

        alert.setContentText(
                message
        );

        alert.showAndWait();
    }
}