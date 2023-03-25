package edu.wpi.teamc.controllers;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public class HomeController {

  @FXML MFXButton navigateButton;
  @FXML MFXButton signageButton;

  @FXML
  public void initialize() {
    navigateButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SERVICE_REQUEST));
    signageButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE_PAGE));
  }
}
