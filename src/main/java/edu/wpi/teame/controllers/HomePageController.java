package edu.wpi.teame.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class HomePageController {
  @FXML MFXButton menuButton;
  @FXML MFXButton bottomB;
  @FXML MFXButton topB;

  private BooleanProperty hovered = new SimpleBooleanProperty(true);
  // private boolean b = false;

  public void initialize() {

    topB.setVisible(false);
    bottomB.setVisible(false);

    menuButton.setOnMouseEntered(
        event -> {
          topB.setVisible(true);
          bottomB.setVisible(true);
        });
    menuButton.setOnMouseExited(
        event -> {
          topB.setVisible(false);
          bottomB.setVisible(false);
        });
    topB.setOnMouseEntered(
        event -> {
          topB.setVisible(true);
          bottomB.setVisible(true);
        });
    topB.setOnMouseExited(
        event -> {
          topB.setVisible(false);
          bottomB.setVisible(false);
        });

    bottomB.setOnMouseEntered(
        event -> {
          topB.setVisible(true);
          bottomB.setVisible(true);
        });
    bottomB.setOnMouseExited(
        event -> {
          topB.setVisible(false);
          bottomB.setVisible(false);
        });
  }
}
