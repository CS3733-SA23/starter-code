package edu.wpi.teamc.controllers;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class ConferenceController {
  @FXML private Button CONF_submit;
  @FXML private Button CONF_backtohome;
  @FXML private Label CONF_outputtext;
  @FXML private MenuButton RoomSelection;

  @FXML
  public void goHome() {
    CONF_backtohome.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  /*@FXML
  void CONF_submitrequest() {
    CONF_outputtext.setText("Submitted");
  }*/

  @FXML
  void getRoomSelection(ActionEvent event) {}

  @FXML private MenuItem Option1;

  @FXML private MenuItem Option2;

  @FXML
  void getSubmitButton() {
    CONF_submit.setOnMouseClicked(event -> Navigation.navigate(Screen.CONGRATS_PAGE));
  }

  @FXML
  void getOption1(ActionEvent event) {
    RoomSelection.setText("Option 1");
  }

  @FXML
  void getOption2(ActionEvent event) {
    RoomSelection.setText("Option 2");
  }
}
