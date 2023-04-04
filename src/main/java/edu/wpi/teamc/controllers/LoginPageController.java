package edu.wpi.teamc.controllers;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class LoginPageController {

  @FXML private Button GuestPage;

  @FXML private Button AdminPage;

  @FXML private Button clear;

  @FXML
  void getAdminPage(ActionEvent event) {
    Navigation.navigate(Screen.HOME);
  }

  @FXML
  void getGuestPage(ActionEvent event) {
    Navigation.navigate(Screen.GUEST_HOME);
  }

  @FXML
  void getClear(ActionEvent event) {
    Navigation.navigate(Screen.LOGIN);
  }
}
