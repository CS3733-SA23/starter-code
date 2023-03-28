package edu.wpi.teamc.controllers;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class FlowerController {
  @FXML private MFXButton FLOW_backtohome;
  @FXML private MFXButton FLOW_submit;
  @FXML private Label FLOW_outputtext;

  @FXML
  public void initialize() {
    FLOW_backtohome.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  @FXML
  void SubmitRequest() {
    FLOW_outputtext.setText("Submitted");
  }
}
