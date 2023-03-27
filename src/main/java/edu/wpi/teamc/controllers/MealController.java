package edu.wpi.teamc.controllers;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MealController {
  @FXML MFXButton MEAL_backtorequest;
  @FXML MFXButton MEAL_backtohome;
  @FXML MFXButton MEAL_submitrequest;
  @FXML Label MEAL_outputtext;

  @FXML
  public void initialize() {
    MEAL_backtorequest.setOnMouseClicked(event -> Navigation.navigate(Screen.SERVICE_REQUEST));
    MEAL_backtohome.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  @FXML
  void SubmitRequest() {
    MEAL_outputtext.setText("Submitted");
  }
}
