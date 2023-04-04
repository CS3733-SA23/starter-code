package edu.wpi.teamc.controllers;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class HelpController {

  @FXML private Button goHome;

  public void getGoHome() {
    goHome.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }
}
