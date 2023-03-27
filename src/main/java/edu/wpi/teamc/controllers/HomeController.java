package edu.wpi.teamc.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public class HomeController {

  @FXML MFXButton navigateButton;
  @FXML MFXButton signageButton;

  @FXML
  public void initialize() {
<<<<<<< HEAD
    navigateButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SERVICE_REQUEST));
    signageButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE_PAGE));
=======
    // navigateButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SERVICE_REQUEST));
>>>>>>> ae5af00f4b68ad609beb52daebe0bc6cd3652b18
  }
}
