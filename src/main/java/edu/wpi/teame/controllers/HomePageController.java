package edu.wpi.teame.controllers;

import edu.wpi.teame.entities.LoginData;
import edu.wpi.teame.navigation.Navigation;
import edu.wpi.teame.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.awt.*;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class HomePageController {
  @FXML MFXButton serviceRequestButton;
  @FXML MFXButton signageButton;
  @FXML MFXButton menuButton;
  @FXML MFXButton menuBarSignage;
  @FXML MFXButton menuBarServices;
  @FXML MFXButton menuBarHome;
  @FXML MFXButton menuBarMap;
  @FXML MFXButton menuBarExit;
  @FXML MFXButton mapsButton;
  @FXML MFXButton loginButton;
  @FXML TextField username;
  @FXML TextField password;

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
    mapsButton.setOnMouseClicked(event -> Navigation.navigate(Screen.GROUND_FLOOR));

    menuBarSignage.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE_TEXT));
    menuBarServices.setOnMouseClicked(event -> Navigation.navigate(Screen.SERVICE_REQUESTS));
    menuBarHome.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    // menuBarMap.setOnMouseClicked(event -> Navigation.navigate(Screen.MAP)); //Uncomment when Map
    // Page is made
    // menuBarExit.setOnMouseClicked(event -> Navigation.navigate(Screen.EXIT)); //Uncomment when we
    // know where exit goes

    loginButton.setOnMouseClicked(event -> attemptLogin());
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

  public void attemptLogin() {
    // Get the input login info
    LoginData login = new LoginData(username.getText(), password.getText());

    // If the login was successful
    if (login.attemptLogin()) {
      // Hide text fields and button
      password.setVisible(false);
      username.setVisible(false);
      loginButton.setVisible(false);
    }
    else {
      // Clear the fields
      password.clear();
      username.clear();
    }
  }
}
