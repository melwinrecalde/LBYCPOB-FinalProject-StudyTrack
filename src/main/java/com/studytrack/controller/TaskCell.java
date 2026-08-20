package com.studytrack.controller;

import com.studytrack.model.Task;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.VBox;

public class TaskCell extends ListCell<Task> {

    private final VBox container =
            new VBox(6);

    private final Label titleLabel =
            new Label();

    private final Label typeLabel =
            new Label();

    private final Label descriptionLabel =
            new Label();

    private final Label detailsLabel =
            new Label();

    private final Label overdueLabel =
            new Label();

    public TaskCell() {

        container.setPadding(
                new Insets(12)
        );

        container.setSpacing(6);

        titleLabel.setStyle(
                "-fx-font-size: 17px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #222222;"
        );

        typeLabel.setStyle(
                "-fx-font-size: 13px;" +
                        "-fx-text-fill: #444444;"
        );

        descriptionLabel.setWrapText(true);

        descriptionLabel.setStyle(
                "-fx-font-size: 13px;" +
                        "-fx-text-fill: #444444;"
        );

        detailsLabel.setStyle(
                "-fx-font-size: 12px;" +
                        "-fx-text-fill: #555555;"
        );

        overdueLabel.setStyle(
                "-fx-font-size: 13px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-text-fill: #d32f2f;"
        );

        container.setStyle(
                "-fx-border-color: #cccccc;" +
                        "-fx-border-radius: 6;" +
                        "-fx-background-radius: 6;" +
                        "-fx-background-color: white;" +
                        "-fx-padding: 10;"
        );

        container.getChildren().addAll(
                titleLabel,
                typeLabel,
                descriptionLabel,
                detailsLabel,
                overdueLabel
        );
    }

    @Override
    protected void updateItem(
            Task task,
            boolean empty
    ) {

        super.updateItem(
                task,
                empty
        );

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

            detailsLabel.setText(
                    "Due: "
                            + task.getDueDate()
                            + " at "
                            + task.getDueTime()
                            + " | Priority: "
                            + task.getPriority()
                            + " | Status: "
                            + task.getStatus()
            );

            if (task.isOverdue()) {

                overdueLabel.setText(
                        "⚠ OVERDUE"
                );

            } else {

                overdueLabel.setText("");
            }

            setText(null);
            setGraphic(container);

            updateSelectionStyle();
        }
    }

    @Override
    public void updateSelected(
            boolean selected
    ) {

        super.updateSelected(
                selected
        );

        updateSelectionStyle();
    }

    private void updateSelectionStyle() {

        if (isSelected()) {

            container.setStyle(
                    "-fx-border-color: #1976d2;" +
                            "-fx-border-width: 2;" +
                            "-fx-border-radius: 6;" +
                            "-fx-background-radius: 6;" +
                            "-fx-background-color: #e3f2fd;" +
                            "-fx-padding: 10;"
            );

            titleLabel.setStyle(
                    "-fx-font-size: 17px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: #0d47a1;"
            );

            typeLabel.setStyle(
                    "-fx-font-size: 13px;" +
                            "-fx-text-fill: #1565c0;"
            );

            descriptionLabel.setStyle(
                    "-fx-font-size: 13px;" +
                            "-fx-text-fill: #1565c0;"
            );

            detailsLabel.setStyle(
                    "-fx-font-size: 12px;" +
                            "-fx-text-fill: #0d47a1;"
            );

        } else {

            container.setStyle(
                    "-fx-border-color: #cccccc;" +
                            "-fx-border-radius: 6;" +
                            "-fx-background-radius: 6;" +
                            "-fx-background-color: white;" +
                            "-fx-padding: 10;"
            );

            titleLabel.setStyle(
                    "-fx-font-size: 17px;" +
                            "-fx-font-weight: bold;" +
                            "-fx-text-fill: #222222;"
            );

            typeLabel.setStyle(
                    "-fx-font-size: 13px;" +
                            "-fx-text-fill: #444444;"
            );

            descriptionLabel.setStyle(
                    "-fx-font-size: 13px;" +
                            "-fx-text-fill: #444444;"
            );

            detailsLabel.setStyle(
                    "-fx-font-size: 12px;" +
                            "-fx-text-fill: #555555;"
            );
        }
    }
}

