package com.studytrack.controller;

import com.studytrack.model.Task;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

public class TaskCell extends ListCell<Task> {

    private final VBox container = new VBox(5);

    private final Label titleLabel = new Label();
    private final Label typeLabel = new Label();
    private final Label descriptionLabel = new Label();
    private final Label detailsLabel = new Label();

    public TaskCell() {

        container.setPadding(
                new Insets(10)
        );

        titleLabel.setStyle(
                "-fx-font-size: 16px; -fx-font-weight: bold;"
        );

        typeLabel.setStyle(
                "-fx-font-size: 13px;"
        );

        descriptionLabel.setWrapText(true);

        descriptionLabel.setStyle(
                "-fx-font-size: 13px;"
        );

        detailsLabel.setStyle(
                "-fx-font-size: 12px;"
        );

        container.getChildren().addAll(
                titleLabel,
                typeLabel,
                descriptionLabel,
                detailsLabel
        );
    }

    @Override
    protected void updateItem(
            Task task,
            boolean empty
    ) {

        super.updateItem(task, empty);

        if (empty || task == null) {

            setText(null);
            setGraphic(null);

        } else {

            titleLabel.setText(
                    task.getTitle()
            );

            typeLabel.setText(
                    "Type: " + task.getTaskType()
            );

            descriptionLabel.setText(
                    "Description: "
                            + task.getDescription()
            );

            detailsLabel.setText(
                    "Due: "
                            + task.getDueDate()
                            + " | Priority: "
                            + task.getPriority()
                            + " | Status: "
                            + task.getStatus()
            );

            setGraphic(container);
        }
    }
}
