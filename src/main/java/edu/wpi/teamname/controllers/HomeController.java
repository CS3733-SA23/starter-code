package edu.wpi.teamname.controllers;

import static javafx.scene.paint.Color.*;

import edu.wpi.teamname.navigation.Navigation;
import edu.wpi.teamname.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class HomeController {

  // @FXML MFXButton navigateButton;

  @FXML MFXButton signageButton;
  @FXML MFXButton flowersButton;

  @FXML MFXButton mealRequestButton;

  @FXML ImageView logoImage;

  @FXML
  public void initialize() {
    logoImage.setOnMouseEntered(
        event -> {
          //change to wong.jpg
        });
    logoImage.setOnMouseExited(
        event -> {
          //change to teamlogo.jpg
        });

    flowersButton.setOnMouseEntered(
        event -> {
          flowersButton.setStyle(
              "-fx-background-color: #ffffff; -fx-alignment: center-left; -fx-border-color: #192d5a; -fx-border-width: 2;");
          flowersButton.setTextFill(Color.web("#192d5aff", 1.0));
        });
    flowersButton.setOnMouseExited(
        event -> {
          flowersButton.setStyle("-fx-background-color: #192d5aff; -fx-alignment: center-left;");
          flowersButton.setTextFill(WHITE);
        });
    flowersButton.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOWER_REQUEST));

    signageButton.setOnMouseEntered(
        event -> {
          signageButton.setStyle(
              "-fx-background-color: #ffffff; -fx-alignment: center-left; -fx-border-color: #192d5a; -fx-border-width: 2;");
          signageButton.setTextFill(Color.web("#192d5aff", 1.0));
        });
    signageButton.setOnMouseExited(
        event -> {
          signageButton.setStyle("-fx-background-color: #192d5aff; -fx-alignment: center-left;");
          signageButton.setTextFill(WHITE);
        });
    signageButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE_TEXT));

    mealRequestButton.setOnMouseEntered(
        event -> {
          mealRequestButton.setStyle(
              "-fx-background-color: #ffffff; -fx-alignment: center-left; -fx-border-color: #192d5a; -fx-border-width: 2;");
          mealRequestButton.setTextFill(Color.web("#192d5aff", 1.0));
        });
    mealRequestButton.setOnMouseExited(
        event -> {
          mealRequestButton.setStyle(
              "-fx-background-color: #192d5aff; -fx-alignment: center-left;");
          mealRequestButton.setTextFill(WHITE);
        });
    mealRequestButton.setOnMouseClicked(event -> Navigation.navigate(Screen.MEAL_REQUEST));
  }
}
