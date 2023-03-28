package edu.wpi.teamA.controllers;

import edu.wpi.teamA.navigation.Navigation;
import edu.wpi.teamA.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class FlowerController {
  @FXML MFXButton backButton;
  @FXML MFXButton clearButton;
  @FXML MFXButton submitButton;
  @FXML TextField nameText;
  @FXML TextField roomText;
  @FXML TextField commentsText;
  @FXML ComboBox monthCombo;
  @FXML ComboBox dayCombo;
  @FXML ComboBox timeCombo;
  @FXML ComboBox flowerCombo;
  @FXML ComboBox ampmCombo;

  @FXML
  public void initialize() {
    // populating drop downs
    flowerCombo.getItems().removeAll(flowerCombo.getItems());
    flowerCombo.getItems().addAll("Red", "Blue", "Green");

    monthCombo.getItems().removeAll(monthCombo.getItems());
    monthCombo
        .getItems()
        .addAll(
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December");

    dayCombo.getItems().removeAll(dayCombo.getItems());
    dayCombo
        .getItems()
        .addAll(
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24,
            25, 26, 27, 28, 29, 30, 31);

    timeCombo.getItems().removeAll(timeCombo.getItems());
    timeCombo
        .getItems()
        .addAll(
            "12:00", "12:30", "1:00", "1:30", "2:00", "2:30", "3:00", "3:30", "4:00", "4:30",
            "5:00", "5:30", "6:00", "6:30", "7:00", "7:30", "8:00", "8:30", "9:00", "9:30", "10:00",
            "10:30", "11:00", "11:30");

    ampmCombo.getItems().removeAll(ampmCombo.getItems());
    ampmCombo.getItems().addAll("AM", "PM");

    // back button
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SERVICE_REQUEST));

    // error handling
    Alert a = new Alert(AlertType.ERROR);
    submitButton.setOnAction(
        new EventHandler<ActionEvent>() {
          public void handle(ActionEvent event) {
            if (nameText.getText().isEmpty()) {
              a.show();
              return;
            }
            if (roomText.getText().isEmpty()) {
              a.show();
              return;
            }
            if (flowerCombo.getSelectionModel().isEmpty()) {
              a.show();
              return;
            }
            if (monthCombo.getSelectionModel().isEmpty()) {
              a.show();
              return;
            }
            if (dayCombo.getSelectionModel().isEmpty()) {
              a.show();
              return;
            }
            if (timeCombo.getSelectionModel().isEmpty()) {
              a.show();
              return;
            } else {

            }
          }
        });
    clearButton.setOnAction(
        new EventHandler<ActionEvent>() {
          public void handle(ActionEvent event) {
            nameText.clear();
            roomText.clear();
            commentsText.clear();
            monthCombo.getSelectionModel().clearSelection();
            dayCombo.getSelectionModel().clearSelection();
            timeCombo.getSelectionModel().clearSelection();
            flowerCombo.getSelectionModel().clearSelection();
            ampmCombo.getSelectionModel().clearSelection();
          }
        });
  }
}
