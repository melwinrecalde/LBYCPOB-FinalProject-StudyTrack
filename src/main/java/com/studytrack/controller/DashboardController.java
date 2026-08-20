package com.studytrack.controller;

import com.studytrack.model.Task;
import com.studytrack.service.TaskManager;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.ButtonType;
import javafx.event.ActionEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class DashboardController {

    @FXML
    private ListView<String> taskListView;

    private final TaskManager taskManager = TaskManager.getInstance();

    @FXML
    private void initialize() {
        refreshTaskList();
    }

    private void refreshTaskList() {

        taskListView.setItems(
                FXCollections.observableArrayList(
                        taskManager.getAllTasks()
                                .stream()
                                .map(this::formatTask)
                                .toList()
                )
        );
    }

    private String formatTask(Task task) {

        return task.getTitle()
                + " | "
                + task.getTaskType()
                + " | Due: "
                + task.getDueDate()
                + " | "
                + task.getPriority()
                + " | "
                + task.getStatus();
    }

    @FXML
    private void handleAddTask(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/add-task.fxml")
        );

        Scene scene = new Scene(loader.load(), 800, 600);

        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();

        stage.setScene(scene);
        stage.setTitle("StudyTrack - Add Task");
        stage.show();
    }

    @FXML
    private void handleEditTask() throws IOException {

        int selectedIndex =
                taskListView.getSelectionModel().getSelectedIndex();

        if (selectedIndex == -1) {
            showError("Please select a task to edit.");
            return;
        }

        Task selectedTask =
                taskManager.getAllTasks().get(selectedIndex);

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/edit-task.fxml")
        );

        Scene scene = new Scene(loader.load(), 800, 600);

        EditTaskController controller =
                loader.getController();

        controller.setTask(selectedTask);

        Stage stage = new Stage();

        stage.setTitle("StudyTrack - Edit Task");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();

        refreshTaskList();
    }

    @FXML
    private void handleDeleteTask() {

        int selectedIndex =
                taskListView.getSelectionModel().getSelectedIndex();

        if (selectedIndex == -1) {
            showError("Please select a task to delete.");
            return;
        }

        Task selectedTask =
                taskManager.getAllTasks().get(selectedIndex);

        Alert confirmation = new Alert(
                Alert.AlertType.CONFIRMATION
        );

        confirmation.setTitle("StudyTrack");
        confirmation.setHeaderText("Delete Task");
        confirmation.setContentText(
                "Are you sure you want to delete \""
                        + selectedTask.getTitle()
                        + "\"?"
        );

        Optional<ButtonType> result =
                confirmation.showAndWait();

        if (result.isPresent()
                && result.get() == ButtonType.OK) {

            taskManager.deleteTask(selectedTask);

            refreshTaskList();
        }
    }

    @FXML
    private void handleStartStudying(ActionEvent event)
            throws IOException {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/fxml/study-session.fxml")
        );

        Scene scene = new Scene(loader.load(), 800, 600);

        Stage stage = (Stage) ((Node) event.getSource())
                .getScene()
                .getWindow();

        stage.setScene(scene);
        stage.setTitle("StudyTrack - Study Session");
        stage.show();
    }

    private void showError(String message) {

        Alert alert = new Alert(
                Alert.AlertType.ERROR
        );

        alert.setTitle("StudyTrack");
        alert.setHeaderText("Action Failed");
        alert.setContentText(message);
        alert.showAndWait();
    }
}