package com.studytrack.controller;

import com.studytrack.model.Task;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

public class TaskCell extends ListCell<Task> {

    private final VBox container = new VBox(6);

    private final Label titleLabel = new Label();
    private final Label typeLabel = new Label();
    private final Label descriptionLabel = new Label();
    private final Label subjectLabel = new Label();
    private final Label detailsLabel = new Label();

    public TaskCell() {

        container.setPadding(
                new Insets(12)
        );

        container.setSpacing(6);

        titleLabel.setStyle(
                "-fx-font-size: 17px;" +
                        "-fx-font-weight: bold;"
        );

        typeLabel.setStyle(
                "-fx-font-size: 13px;"
        );

        descriptionLabel.setWrapText(true);

        descriptionLabel.setStyle(
                "-fx-font-size: 13px;"
        );

        subjectLabel.setStyle(
                "-fx-font-size: 13px;"
        );

        detailsLabel.setStyle(
                "-fx-font-size: 12px;"
        );

        container.setStyle(
                "-fx-border-color: #cccccc;" +
                        "-fx-border-radius: 6;" +
                        "-fx-background-radius: 6;" +
                        "-fx-padding: 10;"
        );

        container.getChildren().addAll(
                titleLabel,
                typeLabel,
                descriptionLabel,
                subjectLabel,
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
                    "Type: "
                            + task.getTaskType()
            );

            String description =
                    task.getDescription();

            if (description == null
                    || description.isBlank()) {

                descriptionLabel.setText(
                        "Description: None"
                );

            } else {

                descriptionLabel.setText(
                        "Description: "
                                + description
                );
            }

            subjectLabel.setText(
                    "Subject: "
                            + task.getSubject().getName()
            );

            detailsLabel.setText(
                    "Due: "
                            + task.getDueDate()
                            + " | Priority: "
                            + task.getPriority()
                            + " | Status: "
                            + task.getStatus()
            );

            setText(null);
            setGraphic(container);
        }
    }
}