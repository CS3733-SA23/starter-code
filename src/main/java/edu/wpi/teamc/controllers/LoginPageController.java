package edu.wpi.teamc.controllers;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LoginPageController {

  @FXML private Button Map;

  @FXML private Button AdminMapPage;

  @FXML
  void getAdminMapPage(ActionEvent event) {
    Navigation.navigate(Screen.HOME);
  }

  @FXML
  void getMapPage(ActionEvent event) {
    Navigation.navigate(Screen.GUEST_HOME);
  }
}
