package com.studytrack.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {

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