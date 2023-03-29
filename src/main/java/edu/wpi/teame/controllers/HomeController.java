package edu.wpi.teame.controllers;

import static javafx.scene.paint.Color.*;

import edu.wpi.teame.navigation.Navigation;
import edu.wpi.teame.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class HomeController {
  @FXML MFXButton signageButton;
  @FXML MFXButton flowersButton;
  @FXML MFXButton mealRequestButton;

  @FXML ImageView logoImage;

  @FXML
  public void initialize() {
    logoImage.setOnMouseEntered(
        event -> {
          // change to wong.jpg
        });
    logoImage.setOnMouseExited(
        event -> {
          // change to teamlogo.jpg
        });

    mouseSetup(flowersButton);
    flowersButton.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOWER_REQUEST));

    mouseSetup(signageButton);
    signageButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE_TEXT));

    mouseSetup(mealRequestButton);
    mealRequestButton.setOnMouseClicked(event -> Navigation.navigate(Screen.MEAL_REQUEST));
  }

  private void mouseSetup(MFXButton btn) {
    btn.setOnMouseEntered(
        event -> {
          btn.setStyle(
              "-fx-background-color: #ffffff; -fx-alignment: center-left; -fx-border-color: #192d5a; -fx-border-width: 2;");
          btn.setTextFill(Color.web("#192d5aff", 1.0));
        });
    btn.setOnMouseExited(
        event -> {
          btn.setStyle("-fx-background-color: #192d5aff; -fx-alignment: center-left;");
          btn.setTextFill(WHITE);
        });
  }
}
