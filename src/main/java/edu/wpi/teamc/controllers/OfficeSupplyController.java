package edu.wpi.teamc.controllers;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class OfficeSupplyController {
  @FXML private MFXButton OFF_backtohome;
  @FXML private MFXButton OFF_submit;
  @FXML private Label OFF_outputtext;

  @FXML
  public void initialize() {
    OFF_backtohome.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  /*@FXML
  void SubmitRequest() {
    OFF_outputtext.setText("Submitted");
  }*/

  @FXML
  void SubmitRequest() {
    OFF_submit.setOnMouseClicked(event -> Navigation.navigate(Screen.CONGRATS_PAGE));
  }
}
