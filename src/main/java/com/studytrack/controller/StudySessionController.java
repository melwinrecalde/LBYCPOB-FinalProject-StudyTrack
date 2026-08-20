package com.studytrack.controller;

import com.studytrack.model.Task;
import com.studytrack.service.TaskManager;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

public class StudySessionController {

    @FXML
    private ComboBox<Task> taskComboBox;

    @FXML
    private Label activeTaskLabel;

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

    private Task activeTask;

    @FXML
    private void initialize() {

        loadTasks();

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

        activeTaskLabel.setText(
                "Currently Studying: None"
        );
    }

    private void loadTasks() {

        List<Task> tasks =
                taskManager.getAllTasks();

        tasks =
                tasks.stream()
                        .sorted(
                                Comparator
                                        .comparing(
                                                Task::isCompleted
                                        )
                                        .thenComparing(
                                                Task::isOverdue,
                                                Comparator.reverseOrder()
                                        )
                                        .thenComparing(
                                                Task::getDueDate
                                        )
                                        .thenComparing(
                                                Task::getDueTime
                                        )
                        )
                        .toList();

        taskComboBox.setItems(
                FXCollections.observableArrayList(
                        tasks
                )
        );
    }

    @FXML
    private void handleStartSession(
            ActionEvent event
    ) {

        /*
         * Select the task only when starting
         * a completely new session.
         */
        if (timer.getStatus()
                == Timeline.Status.STOPPED) {

            activeTask =
                    taskComboBox.getValue();

            if (activeTask == null) {

                showError(
                        "Please select a task before starting the study session."
                );

                return;
            }
        }

        /*
         * Make sure a task still exists when
         * resuming a paused session.
         */
        if (activeTask == null) {

            activeTask =
                    taskComboBox.getValue();

            if (activeTask == null) {

                showError(
                        "Please select a task before starting the study session."
                );

                return;
            }
        }

        activeTaskLabel.setText(
                "Currently Studying: "
                        + activeTask.getTitle()
        );

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

        activeTask = null;

        activeTaskLabel.setText(
                "Currently Studying: None"
        );
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

    /*
     * Return to Dashboard without creating
     * a new Scene or changing the window size.
     */
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

        Parent root = loader.load();

        Scene scene =
                ((Node) event.getSource())
                        .getScene();

        scene.setRoot(root);

        Stage stage =
                (Stage) scene.getWindow();

        stage.setTitle(
                "StudyTrack - Dashboard"
        );
    }

    private void showError(
            String message
    ) {

        Alert alert =
                new Alert(
                        Alert.AlertType.WARNING
                );

        alert.setTitle(
                "StudyTrack"
        );

        alert.setHeaderText(
                "Study Session"
        );

        alert.setContentText(
                message
        );

        alert.showAndWait();
    }
}