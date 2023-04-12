package edu.wpi.teame.controllers;

import edu.wpi.teame.utilities.ButtonUtilities;
import edu.wpi.teame.utilities.ColorPalette;
import edu.wpi.teame.utilities.Navigation;
import edu.wpi.teame.utilities.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;

public class HomeController {
  @FXML MFXButton signageButton;
  @FXML MFXButton flowersButton;
  @FXML MFXButton mealRequestButton;
  @FXML MFXButton exitButton;

  @FXML MFXButton databaseViewButton;

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

    ButtonUtilities.addButtonHover(exitButton);
    exitButton.setOnMouseClicked(event -> Platform.exit());

    ButtonUtilities.addButtonHover(flowersButton);
    flowersButton.setOnMouseClicked(event -> Navigation.navigate(Screen.FLOWER_REQUEST));

    ButtonUtilities.addButtonHover(signageButton, ColorPalette.DARK_BLUE, ColorPalette.WHITE, true);
    signageButton.setOnMouseClicked(event -> Navigation.navigate(Screen.SIGNAGE_TEXT));

    databaseViewButton.setOnMouseClicked(event -> Navigation.navigate(Screen.DATABASE_VIEW));

    ButtonUtilities.addButtonHover(
        mealRequestButton, ColorPalette.DARK_BLUE, ColorPalette.WHITE, true);
    mealRequestButton.setOnMouseClicked(event -> Navigation.navigate(Screen.MEAL_REQUEST));
  }
}
