package com.studytrack;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StudyTrackApplication extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                StudyTrackApplication.class.getResource("/fxml/dashboard.fxml")
        );

        Scene scene = new Scene(loader.load(), 800, 600);

        stage.setTitle("StudyTrack");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}