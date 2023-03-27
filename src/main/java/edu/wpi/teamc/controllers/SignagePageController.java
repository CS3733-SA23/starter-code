package edu.wpi.teamc.controllers;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

/** */
public class SignagePageController {
  @FXML MFXButton backHome;

  /**
   * Method run when controller is initialized
   */
  @FXML
  public void initialize() {
    backHome.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

}
