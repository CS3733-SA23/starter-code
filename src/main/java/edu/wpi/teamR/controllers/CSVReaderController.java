package edu.wpi.teamR.controllers;

import edu.wpi.teamR.navigation.Navigation;
import edu.wpi.teamR.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import java.io.File;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

public class CSVReaderController {
  @FXML MFXButton backButton;
  @FXML MFXButton fileButton;
  @FXML Text fileText;
  static File selectedFile;

  @FXML
  public void initialize() {
    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    fileButton.setOnAction(event -> openFile());
  }

  public void openFile() {
    FileChooser fileChooser = new FileChooser();
    fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
    fileChooser.setTitle("Open File");
    selectedFile = fileChooser.showOpenDialog(fileButton.getScene().getWindow());
    fileText.setText(selectedFile.getName());
  }
}
