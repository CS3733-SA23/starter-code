package edu.wpi.teamc.controllers;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class FurnitureController {
  @FXML private MFXButton FURN_backtohome;
  @FXML private MFXButton FURN_submit;
  @FXML private Label FURN_outputtext;

  @FXML private MenuItem furnSelect1;

  @FXML private MenuButton furnSelect;

  @FXML
  void optionOneSelected(ActionEvent event) {
    furnSelect.setText("Option 1 Selected");
  }

  @FXML
  public void initialize() {
    FURN_backtohome.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  @FXML
  void SubmitRequest() {
    FURN_submit.setOnMouseClicked(event -> Navigation.navigate(Screen.CONGRATS_PAGE));
  }
}
