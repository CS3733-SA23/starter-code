package edu.wpi.romanticraijuu.controllers;

import edu.wpi.romanticraijuu.navigation.Navigation;
import edu.wpi.romanticraijuu.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import org.controlsfx.control.PopOver;

public class HomeController {

  @FXML MFXButton navigateButton;
  @FXML MFXButton mealButton;
  @FXML MFXButton furnitureButton;
  @FXML MFXButton helpButton;
  @FXML MFXButton exitButton;
  @FXML BorderPane borderPane;
  @FXML AnchorPane anchorPane;

  private static Parent root;

  @FXML
  public void initialize() {
    borderPane.setStyle("-fx-background-color: #FFFFFF;");

    navigateButton.setOnMouseClicked(event -> Navigation.navigate(Screen.MEAL_REQUEST));
    navigateButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE));
    mealButton.setOnMouseClicked(event -> Navigation.navigate(Screen.MEAL_REQUEST));
    exitButton.setOnMouseClicked(actionEvent -> Platform.exit());
    furnitureButton.setOnMouseClicked(event -> Navigation.navigate(Screen.FURNITURE_REQUEST));

    helpButton.setOnAction(
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
        new FXMLLoader(getClass().getResource("/edu/wpi/romanticraijuu/views/Help.fxml"));
    Parent help = loader.load();
    helpPopup.setContentNode(help);
    helpPopup.setArrowLocation(PopOver.ArrowLocation.TOP_CENTER);
    helpPopup.setAutoHide(true);
    helpPopup.show(helpButton);
  }
}
