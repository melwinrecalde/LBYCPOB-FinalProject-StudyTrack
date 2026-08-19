package com.studytrack.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

public class DashboardController {

    @FXML
    private void handleStartStudying() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("StudyTrack");
        alert.setHeaderText("Study Session");
        alert.setContentText("Your study session is ready to begin!");
        alert.showAndWait();
    }
}