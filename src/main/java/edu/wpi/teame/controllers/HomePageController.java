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
  @FXML MFXButton menuBarSignage;
  @FXML MFXButton menuBarServices;
  @FXML MFXButton menuBarHome;
  @FXML MFXButton menuBarMap;
  @FXML MFXButton menuBarExit;

  private BooleanProperty hovered = new SimpleBooleanProperty(true);
  // private boolean b = false;

  public void initialize() {

    menuBarSignage.setVisible(false);
    menuBarServices.setVisible(false);
    menuBarHome.setVisible(false);
    menuBarMap.setVisible(false);
    menuBarExit.setVisible(false);

    menuButton.setOnMouseEntered(
        event -> {
          menuBarSignage.setVisible(true);
          menuBarServices.setVisible(true);
          menuBarHome.setVisible(true);
          menuBarMap.setVisible(true);
          menuBarExit.setVisible(true);
        });
    menuButton.setOnMouseExited(
        event -> {
          menuBarSignage.setVisible(false);
          menuBarServices.setVisible(false);
          menuBarHome.setVisible(false);
          menuBarMap.setVisible(false);
          menuBarExit.setVisible(false);
        });

    menuBarSignage.setOnMouseEntered(
        event -> {
          menuBarSignage.setVisible(true);
          menuBarServices.setVisible(true);
          menuBarHome.setVisible(true);
          menuBarMap.setVisible(true);
          menuBarExit.setVisible(true);
        });
    menuBarSignage.setOnMouseExited(
        event -> {
          menuBarSignage.setVisible(false);
          menuBarServices.setVisible(false);
          menuBarHome.setVisible(false);
          menuBarMap.setVisible(false);
          menuBarExit.setVisible(false);
        });

    menuBarServices.setOnMouseEntered(
        event -> {
          menuBarSignage.setVisible(true);
          menuBarServices.setVisible(true);
          menuBarHome.setVisible(true);
          menuBarMap.setVisible(true);
          menuBarExit.setVisible(true);
        });
    menuBarServices.setOnMouseExited(
        event -> {
          menuBarSignage.setVisible(false);
          menuBarServices.setVisible(false);
          menuBarHome.setVisible(false);
          menuBarMap.setVisible(false);
          menuBarExit.setVisible(false);
        });

    menuBarHome.setOnMouseEntered(
        event -> {
          menuBarSignage.setVisible(true);
          menuBarServices.setVisible(true);
          menuBarHome.setVisible(true);
          menuBarMap.setVisible(true);
          menuBarExit.setVisible(true);
        });
    menuBarHome.setOnMouseExited(
        event -> {
          menuBarSignage.setVisible(false);
          menuBarServices.setVisible(false);
          menuBarHome.setVisible(false);
          menuBarMap.setVisible(false);
          menuBarExit.setVisible(false);
        });

    menuBarMap.setOnMouseEntered(
        event -> {
          menuBarSignage.setVisible(true);
          menuBarServices.setVisible(true);
          menuBarHome.setVisible(true);
          menuBarMap.setVisible(true);
          menuBarExit.setVisible(true);
        });
    menuBarMap.setOnMouseExited(
        event -> {
          menuBarSignage.setVisible(false);
          menuBarServices.setVisible(false);
          menuBarHome.setVisible(false);
          menuBarMap.setVisible(false);
          menuBarExit.setVisible(false);
        });

    menuBarExit.setOnMouseEntered(
        event -> {
          menuBarSignage.setVisible(true);
          menuBarServices.setVisible(true);
          menuBarHome.setVisible(true);
          menuBarMap.setVisible(true);
          menuBarExit.setVisible(true);
        });
    menuBarExit.setOnMouseExited(
        event -> {
          menuBarSignage.setVisible(false);
          menuBarServices.setVisible(false);
          menuBarHome.setVisible(false);
          menuBarMap.setVisible(false);
          menuBarExit.setVisible(false);
        });
  }
}
