package edu.wpi.teamc.controllers;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class MealController {
  @FXML private MFXButton MEAL_backtohome;
  @FXML private MFXButton MEAL_submit;
  @FXML private Label MEAL_outputtext;

  @FXML
  public void initialize() {
    MEAL_backtohome.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  @FXML
  void SubmitRequest() {
    MEAL_outputtext.setText("Submitted");
  }
}
