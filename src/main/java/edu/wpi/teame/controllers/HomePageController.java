package edu.wpi.teame.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class HomePageController {
  @FXML MFXButton serviceRequestButton;
  @FXML MFXButton signageButton;

  private BooleanProperty hovered = new SimpleBooleanProperty(true);
  // private boolean b = false;

  public void initialize() {

    // signageButton.setVisible(true);
    serviceRequestButton.setOnMouseEntered(event -> signageButton.setVisible(true));
    serviceRequestButton.setOnMouseExited(event -> signageButton.setVisible(false));

    signageButton.setOnMouseEntered(event -> signageButton.setVisible(true));
    signageButton.setOnMouseExited(event -> signageButton.setVisible(false));
  }
}
