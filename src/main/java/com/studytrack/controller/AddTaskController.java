package com.studytrack.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class AddTaskController {

    @FXML
    private TextField titleField;

    @FXML
    private TextArea descriptionArea;

    @FXML
    private DatePicker dueDatePicker;

    @FXML
    private ComboBox<String> taskTypeComboBox;

    @FXML
    private ComboBox<String> priorityComboBox;

    @FXML
    private ComboBox<String> statusComboBox;

    @FXML
    private ComboBox<String> subjectComboBox;

    @FXML
    private void initialize() {

        taskTypeComboBox.getItems().addAll(
                "Assignment",
                "Quiz",
                "Project"
        );

        priorityComboBox.getItems().addAll(
                "Low",
                "Medium",
                "High"
        );

        statusComboBox.getItems().addAll(
                "Pending",
                "In Progress",
                "Completed"
        );

        subjectComboBox.getItems().addAll(
                "Object-Oriented Programming",
                "Web Development",
                "Database Systems"
        );

        taskTypeComboBox.getSelectionModel().selectFirst();
        priorityComboBox.getSelectionModel().select("Medium");
        statusComboBox.getSelectionModel().select("Pending");
        subjectComboBox.getSelectionModel().selectFirst();
    }
}