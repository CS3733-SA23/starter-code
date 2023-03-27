package edu.wpi.teamc.controllers;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

<<<<<<< HEAD
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

=======
  public void initialize() {}
>>>>>>> ae5af00f4b68ad609beb52daebe0bc6cd3652b18
}
