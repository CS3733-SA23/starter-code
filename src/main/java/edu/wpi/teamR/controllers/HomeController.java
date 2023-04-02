package edu.wpi.teamR.controllers;

import edu.wpi.teamR.navigation.Navigation;
import edu.wpi.teamR.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.*;
import org.controlsfx.control.PopOver;

public class HomeController {

  @FXML MFXButton navigateButton;
  @FXML MFXButton mealButton;
  @FXML MFXButton furnitureButton;
  @FXML MenuItem exitButton;
  @FXML MenuItem about;
  @FXML BorderPane borderPane;

  private static Parent root;

  @FXML
  public void initialize() {
    navigateButton.setOnMouseClicked(event -> Navigation.navigate(Screen.MEAL_REQUEST));
    navigateButton.setOnMouseEntered(event -> {});
    navigateButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE));
    mealButton.setOnMouseClicked(event -> Navigation.navigate(Screen.MEAL_REQUEST));
    exitButton.setOnAction(actionEvent -> Platform.exit());
    furnitureButton.setOnMouseClicked(event -> Navigation.navigate(Screen.FURNITURE_REQUEST));

    about.setOnAction(
        event -> {
          try {
            help();
          } catch (IOException e) {
            e.printStackTrace();
          }
        });
  }

  @FXML
  private void help() throws IOException {
    PopOver helpPopup = new PopOver();
    final FXMLLoader loader =
        new FXMLLoader(getClass().getResource("/edu/wpi/teamR/views/Help.fxml"));
    Parent help = loader.load();
    helpPopup.setContentNode(help);
    helpPopup.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
    helpPopup.setAutoHide(true);
    helpPopup.show(borderPane);
  }
}
