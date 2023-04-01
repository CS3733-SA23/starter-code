package edu.wpi.teamA.controllers;

import edu.wpi.teamA.navigation.Navigation;
import edu.wpi.teamA.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public class HeaderController {

  @FXML MFXButton backButton;
  @FXML MFXButton exitButton;

  @FXML
  public void initialize() {}

  public void backButton(Screen screen) {
    backButton.setOnMouseClicked(event -> Navigation.navigate(screen));
  }
}
