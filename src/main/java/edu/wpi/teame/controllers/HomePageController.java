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
    menuDropDownVisibilty(false);

    menuButton.setOnMouseEntered(
        event -> {
          menuDropDownVisibilty(true);
        });
    menuButton.setOnMouseExited(
        event -> {
          menuDropDownVisibilty(false);
        });

    menuBarSignage.setOnMouseEntered(
        event -> {
          menuDropDownVisibilty(true);
        });
    menuBarSignage.setOnMouseExited(
        event -> {
          menuDropDownVisibilty(false);
        });

    menuBarServices.setOnMouseEntered(
        event -> {
          menuDropDownVisibilty(true);
        });
    menuBarServices.setOnMouseExited(
        event -> {
          menuDropDownVisibilty(false);
        });

    menuBarHome.setOnMouseEntered(
        event -> {
          menuDropDownVisibilty(true);
        });
    menuBarHome.setOnMouseExited(
        event -> {
          menuDropDownVisibilty(false);
        });

    menuBarMap.setOnMouseEntered(
        event -> {
          menuDropDownVisibilty(true);
        });
    menuBarMap.setOnMouseExited(
        event -> {
          menuDropDownVisibilty(false);
        });

    menuBarExit.setOnMouseEntered(
        event -> {
          menuDropDownVisibilty(true);
        });
    menuBarExit.setOnMouseExited(
        event -> {
          menuDropDownVisibilty(false);
        });
  }

  public void menuDropDownVisibilty(boolean bool) {
    menuBarSignage.setVisible(bool);
    menuBarServices.setVisible(bool);
    menuBarHome.setVisible(bool);
    menuBarMap.setVisible(bool);
    menuBarExit.setVisible(bool);
  }
}
