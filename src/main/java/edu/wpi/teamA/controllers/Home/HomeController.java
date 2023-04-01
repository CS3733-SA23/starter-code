package edu.wpi.teamA.controllers.Home;

import javafx.fxml.FXML;
import javafx.scene.Parent;

public class HomeController {

  // HeaderController headerController = new HeaderController();
  @FXML Parent header;
  @FXML Parent footer;

  @FXML
  public void initialize() {
    // System.out.println(headerController.getTitle());
    // headerController.initialize();
    // HeaderController.setPageTitle("Home");
    // navigateButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HEADER));
  }
}
