package edu.wpi.teame.controllers;

import edu.wpi.teame.utilities.Navigation;
import edu.wpi.teame.utilities.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public class ServiceRequestController {

  @FXML MFXButton backButton;
  @FXML MFXButton menuButton;
  @FXML MFXButton menuBarSignage;
  @FXML MFXButton menuBarServices;
  @FXML MFXButton menuBarHome;
  @FXML MFXButton menuBarMap;
  @FXML MFXButton menuBarExit;

  @FXML
  public void initialize() {
    menuDropDownVisibility(false);

    showMenuButtonsWhenHovered(menuButton);
    showMenuButtonsWhenHovered(menuBarSignage);
    showMenuButtonsWhenHovered(menuBarServices);
    showMenuButtonsWhenHovered(menuBarHome);
    showMenuButtonsWhenHovered(menuBarMap);
    showMenuButtonsWhenHovered(menuBarExit);

    menuBarSignage.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE_TEXT));
    menuBarServices.setOnMouseClicked(event -> Navigation.navigate(Screen.SERVICE_REQUESTS));
    menuBarHome.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    // menuBarMap.setOnMouseClicked(event -> Navigation.navigate(Screen.MAP)); //Uncomment when Map
    // Page is made
    // menuBarExit.setOnMouseClicked(event -> Navigation.navigate(Screen.EXIT)); //Uncomment when we
    // know where exit goes

    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  public void menuDropDownVisibility(boolean bool) {
    menuBarSignage.setVisible(bool);
    menuBarServices.setVisible(bool);
    menuBarHome.setVisible(bool);
    menuBarMap.setVisible(bool);
    menuBarExit.setVisible(bool);
  }

  public void showMenuButtonsWhenHovered(MFXButton button) {
    button.setOnMouseEntered(
        event -> {
          menuDropDownVisibility(true);
        });
    button.setOnMouseExited(
        event -> {
          menuDropDownVisibility(false);
        });
  }
}
