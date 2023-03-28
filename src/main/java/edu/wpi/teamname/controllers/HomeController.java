package edu.wpi.teamname.controllers;

import edu.wpi.teamname.navigation.Navigation;
import edu.wpi.teamname.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public class HomeController {

  // @FXML MFXButton navigateButton;

  @FXML MFXButton signageButton;
  @FXML MFXButton flowersButton;
  @FXML MFXButton mealButton;

  @FXML
  public void initialize() {


    flowersButton.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOWER_REQUEST));

    // navigateButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SERVICE_REQUEST));
    signageButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE_TEXT));

  }
}
