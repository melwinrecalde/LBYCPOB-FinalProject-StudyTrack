package com.studytrack;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StudyTrackApplication extends Application {

    public static final double WINDOW_WIDTH = 1000;
    public static final double WINDOW_HEIGHT = 700;

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                StudyTrackApplication.class.getResource("/fxml/dashboard.fxml")
        );

        Scene scene = new Scene(
                loader.load(),
                WINDOW_WIDTH,
                WINDOW_HEIGHT
        );

        stage.setTitle("StudyTrack");

        // Keep the application window consistent
        stage.setMinWidth(WINDOW_WIDTH);
        stage.setMinHeight(WINDOW_HEIGHT);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}