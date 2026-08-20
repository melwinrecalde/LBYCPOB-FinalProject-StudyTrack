package com.studytrack.controller;

import com.studytrack.model.Task;
import com.studytrack.service.TaskManager;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;

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
    private void handleStartStudying(ActionEvent event) throws IOException {

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
}