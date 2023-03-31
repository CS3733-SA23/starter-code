package edu.wpi.teamc.controllers;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class CongratsController {
  @FXML private Button mainMenuButton;

  @FXML
  public void getMainMenu() {
    mainMenuButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }
}
