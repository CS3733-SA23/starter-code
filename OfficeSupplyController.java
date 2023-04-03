package edu.wpi.teamc.controllers;

import edu.wpi.teamc.navigation.Navigation;
import edu.wpi.teamc.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;

public class OfficeSupplyController {
  @FXML private MFXButton OFF_backtohome;
  @FXML private MFXButton OFF_submit;
  @FXML private Label OFF_outputtext;
  @FXML private MenuButton OFF_Menu;
  @FXML private MenuItem OFF_Default;
  @FXML private MenuItem OFF_Pen;
  @FXML private MenuItem OFF_Notebook;

  @FXML
  public void initialize() {
    OFF_backtohome.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
  }

  @FXML
  void SubmitRequest() {
    OFF_outputtext.setText("Submitted");
  }

  @FXML
  void OFF_Choice0() {
    OFF_Menu.setText("-Please Select Office Supply-");
  }

  @FXML
  void OFF_Choice1() {
    OFF_Menu.setText("Pen");
  }

  @FXML
  void OFF_Choice2() {
    OFF_Menu.setText("Notebook");
  }
}
