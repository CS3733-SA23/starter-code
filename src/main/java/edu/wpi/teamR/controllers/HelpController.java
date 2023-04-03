package edu.wpi.teamR.controllers;

import javafx.fxml.FXML;
import javafx.stage.Stage;

public class HelpController {

  @FXML
  public void initialize() {}

  @FXML
  public void close(Stage primaryStage) {
    primaryStage.close();
  }

  public void start(Stage primaryStage) {
    primaryStage.show();
  }
}
