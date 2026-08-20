package com.studytrack.controller;

import com.studytrack.model.Task;
import com.studytrack.service.TaskManager;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class StudySessionController {

    @FXML
    private ComboBox<Task> taskComboBox;

    @FXML
    private Label timerLabel;

    @FXML
    private Button startSessionButton;

    @FXML
    private Button pauseSessionButton;

    @FXML
    private Button stopSessionButton;

    @FXML
    private Button backToDashboardButton;

    private final TaskManager taskManager =
            TaskManager.getInstance();

    private Timeline timer;

    private int elapsedSeconds = 0;

    /*
     * Stores the task selected when the
     * study session begins.
     */
    private Task activeTask;

    @FXML
    private void initialize() {

        taskComboBox.getItems().setAll(
                taskManager.getAllTasks()
        );

        /*
         * Use the same task tile format as
         * the Dashboard.
         */
        taskComboBox.setCellFactory(
                listView -> {

                    TaskCell cell =
                            new TaskCell();

                    cell.setPrefHeight(130);

                    return cell;
                }
        );

        TaskCell buttonCell =
                new TaskCell();

        buttonCell.setPrefHeight(130);

        taskComboBox.setButtonCell(
                buttonCell
        );

        timer = new Timeline(
                new KeyFrame(
                        Duration.seconds(1),
                        event -> updateTimer()
                )
        );

        timer.setCycleCount(
                Timeline.INDEFINITE
        );

        pauseSessionButton.setDisable(true);
        stopSessionButton.setDisable(true);
    }

    @FXML
    private void handleStartSession(
            ActionEvent event
    ) {

        /*
         * Only select a task when starting
         * a brand-new session.
         */
        if (timer.getStatus()
                == Timeline.Status.STOPPED) {

            activeTask =
                    taskComboBox.getValue();

            if (activeTask == null) {
                return;
            }
        }

        /*
         * If the timer is paused, continue
         * using the same active task.
         */
        if (activeTask == null) {

            activeTask =
                    taskComboBox.getValue();

            if (activeTask == null) {
                return;
            }
        }

        if (timer.getStatus()
                != Timeline.Status.RUNNING) {

            timer.play();

            startSessionButton.setDisable(true);

            pauseSessionButton.setDisable(false);

            stopSessionButton.setDisable(false);
        }
    }

    @FXML
    private void handlePauseSession(
            ActionEvent event
    ) {

        if (timer.getStatus()
                == Timeline.Status.RUNNING) {

            timer.pause();

            startSessionButton.setDisable(false);

            startSessionButton.setText(
                    "Resume Session"
            );

            pauseSessionButton.setDisable(true);
        }
    }

    @FXML
    private void handleStopSession(
            ActionEvent event
    ) {

        timer.stop();

        elapsedSeconds = 0;

        timerLabel.setText(
                "00:00:00"
        );

        startSessionButton.setText(
                "Start Session"
        );

        startSessionButton.setDisable(false);

        pauseSessionButton.setDisable(true);

        stopSessionButton.setDisable(true);

        /*
         * Clear the active task so the next
         * session can select a different task.
         */
        activeTask = null;
    }

    private void updateTimer() {

        elapsedSeconds++;

        int hours =
                elapsedSeconds / 3600;

        int minutes =
                (elapsedSeconds % 3600) / 60;

        int seconds =
                elapsedSeconds % 60;

        timerLabel.setText(
                String.format(
                        "%02d:%02d:%02d",
                        hours,
                        minutes,
                        seconds
                )
        );
    }

    @FXML
    private void handleBackToDashboard(
            ActionEvent event
    ) throws IOException {

        if (timer != null) {

            timer.stop();
        }

        FXMLLoader loader =
                new FXMLLoader(
                        getClass().getResource(
                                "/fxml/dashboard.fxml"
                        )
                );

        Scene scene =
                new Scene(
                        loader.load(),
                        800,
                        600
                );

        Stage stage =
                (Stage)
                        ((Node) event.getSource())
                                .getScene()
                                .getWindow();

        stage.setScene(scene);

        stage.setTitle(
                "StudyTrack - Dashboard"
        );

        stage.show();
    }
}

