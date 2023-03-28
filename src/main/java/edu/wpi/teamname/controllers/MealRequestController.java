package edu.wpi.teamname.controllers;

import edu.wpi.teamname.navigation.Navigation;
import edu.wpi.teamname.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;

public class MealRequestController {
  @FXML MFXButton backButton;
  @FXML MFXButton cancelButton;
  @FXML MFXButton submitButton;
  @FXML MFXTextField deliveryTime;
  @FXML MFXTextField foodSelection;
  @FXML MFXTextField notes;
  @FXML MFXTextField patientName;
  @FXML MFXTextField roomNumber;
  @FXML MFXTextField sideSelection;

  @FXML
  public void initialize() {
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    cancelButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }
}
