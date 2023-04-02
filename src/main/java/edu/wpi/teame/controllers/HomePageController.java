package edu.wpi.teame.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import edu.wpi.teame.navigation.Navigation;
import edu.wpi.teame.navigation.Screen;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class HomePageController {
  @FXML
  MFXButton serviceRequestButton;
  @FXML
  MFXButton signageButton;
  @FXML
  MFXButton menuButton;
  @FXML
  MFXButton menuBarSignage;
  @FXML
  MFXButton menuBarServices;
  @FXML
  MFXButton menuBarHome;
  @FXML
  MFXButton menuBarMap;
  @FXML
  MFXButton menuBarExit;

  public void initialize() {
    menuDropDownVisibilty(false);

    showMenuButtonsWhenHovered(menuButton);
    showMenuButtonsWhenHovered(menuBarSignage);
    showMenuButtonsWhenHovered(menuBarServices);
    showMenuButtonsWhenHovered(menuBarHome);
    showMenuButtonsWhenHovered(menuBarMap);
    showMenuButtonsWhenHovered(menuBarExit);
    serviceRequestButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SERVICE_REQUESTS));
    signageButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE_TEXT));
  }

  public void menuDropDownVisibilty(boolean bool) {
    menuBarSignage.setVisible(bool);
    menuBarServices.setVisible(bool);
    menuBarHome.setVisible(bool);
    menuBarMap.setVisible(bool);
    menuBarExit.setVisible(bool);
  }

  public void showMenuButtonsWhenHovered(MFXButton button) {
    button.setOnMouseEntered(
            event -> {
              menuDropDownVisibilty(true);
            });
    button.setOnMouseExited(
            event -> {
              menuDropDownVisibilty(false);
            });
  }
}
