package edu.wpi.teamc.controllers;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

/** */
public class SignagePageController {
  @FXML MFXButton backHome;

  @FXML
  public void initialize() {
    backHome.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }
  //  @FXML private void goHome(ActionEvent event1) {
  //    event1.consume();
  //    backHome.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  //  }

}
