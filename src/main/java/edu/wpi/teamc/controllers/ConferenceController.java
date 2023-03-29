package edu.wpi.teamc.controllers;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ConferenceController {
  @FXML private Button CONF_submit;
  @FXML private Button CONF_backtohome;
  @FXML private Label CONF_outputtext;

  @FXML
  public void initialize() {
    CONF_backtohome.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  /*@FXML
  void CONF_submitrequest() {
    CONF_outputtext.setText("Submitted");
  }*/

  @FXML
  void getSubmitButton() {
    CONF_submit.setOnMouseClicked(event -> Navigation.navigate(Screen.CONGRATS_PAGE));
  }
}
