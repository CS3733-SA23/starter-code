package edu.wpi.romanticraijuu.controllers;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class HelpController extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @FXML
  public void initialize() {}

  @FXML
  public void close(Stage primaryStage) {
    primaryStage.close();
  }

  @Override
  public void start(Stage primaryStage) {
    primaryStage.show();
  }
}
