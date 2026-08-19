package com.studytrack.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

public class DashboardController {

    @FXML
    private void handleStartStudying(ActionEvent event) throws Exception {

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