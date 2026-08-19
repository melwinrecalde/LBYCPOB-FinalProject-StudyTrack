package com.studytrack;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class StudyTrackApplication extends Application {

    @Override
    public void start(Stage stage) {
        Label label = new Label("StudyTrack");

        Scene scene = new Scene(label, 800, 600);

        stage.setTitle("StudyTrack");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}