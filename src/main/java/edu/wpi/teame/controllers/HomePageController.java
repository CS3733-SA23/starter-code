package edu.wpi.teame.controllers;

import static javafx.scene.paint.Color.WHITE;

import edu.wpi.teame.Database.SQLRepo;
import edu.wpi.teame.entities.LoginData;
import edu.wpi.teame.utilities.Navigation;
import edu.wpi.teame.utilities.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

public class HomePageController {
  @FXML MFXButton serviceRequestButton;
  @FXML MFXButton signageButton;
  @FXML MFXButton databaseViewButton;
  @FXML MFXButton mapsButton;
  @FXML MFXButton loginButton;
  @FXML TextField username;
  @FXML TextField password;
  @FXML MFXButton menuButton;
  @FXML MFXButton menuBarHome;
  @FXML MFXButton menuBarServices;
  @FXML MFXButton menuBarMaps;
  @FXML MFXButton menuBarDatabase;
  @FXML MFXButton menuBarSignage;
  @FXML MFXButton menuBarBlank;
  @FXML MFXButton menuBarExit;

  Boolean loggedIn;

  boolean menuVisibilty = false;

  public void initialize() {
    SQLRepo.INSTANCE.connectToDatabase("teame", "teame50");

    serviceRequestButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SERVICE_REQUESTS));
    signageButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE_TEXT));
    databaseViewButton.setOnMouseClicked(event -> Navigation.navigate(Screen.MAP_DATA_EDITOR));
    mapsButton.setOnMouseClicked(event -> Navigation.navigate(Screen.MAP));

    menuBarSignage.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE_TEXT));
    menuBarServices.setOnMouseClicked(event -> Navigation.navigate(Screen.SERVICE_REQUESTS));
    menuBarHome.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    menuBarMaps.setOnMouseClicked(event -> Navigation.navigate(Screen.MAP));
    menuBarDatabase.setOnMouseClicked(event -> Navigation.navigate((Screen.MAP_DATA_EDITOR)));
    menuBarExit.setOnMouseClicked(event -> Platform.exit()); // Uncomment when we
    // know where exit goes

    loggedIn = false;
    loginButton.setOnMouseClicked(event -> attemptLogin());

    // Initially set the menu bar to invisible
    menuBarVisible(false);

    // When the menu button is clicked, invert the value of menuVisibility and set the menu bar to
    // that value
    // (so each time the menu button is clicked it changes the visibility of menu bar back and
    // forth)
    menuButton.setOnMouseClicked(
        event -> {
          menuVisibilty = !menuVisibilty;
          menuBarVisible(menuVisibilty);
        });

    // Navigation controls for the button in the menu bar
    menuBarHome.setOnMouseClicked(
        event -> {
          Navigation.navigate(Screen.HOME);
          menuVisibilty = !menuVisibilty;
        });
    menuBarServices.setOnMouseClicked(event -> Navigation.navigate(Screen.SERVICE_REQUESTS));
    menuBarSignage.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE_TEXT));
    menuBarMaps.setOnMouseClicked(event -> Navigation.navigate(Screen.MAP));
    menuBarDatabase.setOnMouseClicked(event -> Navigation.navigate(Screen.DATABASE_VIEW));
    menuBarExit.setOnMouseClicked((event -> Platform.exit()));

    // makes the buttons get highlighted when the mouse hovers over them
    mouseSetup(menuBarHome);
    mouseSetup(menuBarServices);
    mouseSetup(menuBarMaps);
    mouseSetup(menuBarDatabase);
    mouseSetup(menuBarExit);
    mouseSetup(serviceRequestButton);
    mouseSetup(menuBarSignage);
    mouseSetup(signageButton);
    mouseSetup(mapsButton);
    mouseSetup(databaseViewButton);
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
      // Set loggedIn as true
      loggedIn = true;

    } else {
      // Clear the fields
      password.clear();
      username.clear();
    }
  }

  private void mouseSetup(MFXButton btn) {
    btn.setOnMouseEntered(
        event -> {
          btn.setStyle(
              "-fx-background-color: #ffffff; -fx-alignment: center; -fx-border-color: #192d5a; -fx-border-width: 2;");
          btn.setTextFill(Color.web("#192d5aff", 1.0));
        });
    btn.setOnMouseExited(
        event -> {
          btn.setStyle("-fx-background-color: #192d5aff; -fx-alignment: center;");
          btn.setTextFill(WHITE);
        });
  }

  public void menuBarVisible(boolean bool) {
    menuBarHome.setVisible(bool);
    menuBarServices.setVisible(bool);
    menuBarSignage.setVisible(bool);
    menuBarMaps.setVisible(bool);
    menuBarDatabase.setVisible(bool);
    menuBarExit.setVisible(bool);
    menuBarBlank.setVisible(bool);
  }
}
