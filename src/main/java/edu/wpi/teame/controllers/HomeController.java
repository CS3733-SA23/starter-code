package edu.wpi.teame.controllers;

import static javafx.scene.paint.Color.*;

import edu.wpi.teame.navigation.Navigation;
import edu.wpi.teame.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class HomeController {
  @FXML MFXButton signageButton;
  @FXML MFXButton flowersButton;
  @FXML MFXButton mealRequestButton;
  @FXML MFXButton exitButton;

<<<<<<< HEAD
  @FXML MFXButton databaseViewButton;

=======
>>>>>>> main
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

    navMouseSetup(exitButton);
    exitButton.setOnMouseClicked(event -> Platform.exit());

    mouseSetup(flowersButton);
    flowersButton.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOWER_REQUEST));

    mouseSetup(signageButton);
    signageButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE_TEXT));

    mouseSetup(mealRequestButton);
    mealRequestButton.setOnMouseClicked(event -> Navigation.navigate(Screen.MEAL_REQUEST));
<<<<<<< HEAD

    mouseSetup(databaseViewButton);
    databaseViewButton.setOnMouseClicked(event -> Navigation.navigate(Screen.DATABASE_VIEW));
=======
>>>>>>> main
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

  private void navMouseSetup(MFXButton btn) { // for buttons such as exit, back, cancel, etc
    btn.setOnMouseEntered(
        event -> {
          btn.setStyle(
              "-fx-background-color: #ffffff; -fx-alignment: center; -fx-border-color: #3b63a5; -fx-border-width: 2;");
          btn.setTextFill(Color.web("#3b63a5", 1.0));
        });
    btn.setOnMouseExited(
        event -> {
          btn.setStyle("-fx-background-color: #3b63a5; -fx-alignment: center;");
          btn.setTextFill(WHITE);
        });
  }
}
