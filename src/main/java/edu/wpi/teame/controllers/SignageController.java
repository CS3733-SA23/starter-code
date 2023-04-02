package edu.wpi.teame.controllers;

import edu.wpi.teame.navigation.Navigation;
import edu.wpi.teame.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public class SignageController {
  @FXML MFXButton returnButtonSignage;

  @FXML
  public void initialize() {
    returnButtonSignage.setOnMouseClicked(event -> Navigation.navigate(Screen.SERVICE_REQUESTS));
  }
}
