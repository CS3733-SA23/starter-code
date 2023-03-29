package edu.wpi.teamc.controllers;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class FurnitureController {
  @FXML private MFXButton FURN_backtohome;
  @FXML private MFXButton FURN_submit;
  @FXML private Label FURN_outputtext;

  @FXML
  public void initialize() {
    FURN_backtohome.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  @FXML
  void SubmitRequest() {
    FURN_outputtext.setText("Submitted");
  }
}
