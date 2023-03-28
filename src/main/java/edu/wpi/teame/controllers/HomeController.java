package edu.wpi.teame.controllers;

import edu.wpi.teame.navigation.Navigation;
import edu.wpi.teame.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public class HomeController {

  // @FXML MFXButton navigateButton;

  @FXML MFXButton signageButton;
  @FXML MFXButton flowersButton;

  @FXML MFXButton mealRequestButton;

  @FXML
  public void initialize() {

    flowersButton.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOWER_REQUEST));

    // navigateButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SERVICE_REQUEST));
    signageButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE_TEXT));
    mealRequestButton.setOnMouseClicked(event -> Navigation.navigate(Screen.MEAL_REQUEST));
  }
}
