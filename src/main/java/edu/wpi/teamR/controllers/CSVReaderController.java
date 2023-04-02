package edu.wpi.teamR.controllers;

import edu.wpi.teamR.navigation.Navigation;
import edu.wpi.teamR.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class CSVReaderController {
  @FXML MFXButton backButton;
  @FXML MFXButton fileButton;
  @FXML Text fileText;

  public void start(Stage primaryStage) {
    FileChooser fileChooser = new FileChooser();
    fileButton.setOnAction(
        event -> {
          File selectedFile = fileChooser.showOpenDialog(primaryStage);
        });
    primaryStage.show();
  }

  @FXML
  public void initialize() {
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }
}
