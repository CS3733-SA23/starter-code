package edu.wpi.teame.controllers.DatabaseEditor;

import edu.wpi.teame.map.Floor;
import edu.wpi.teame.utilities.Navigation;
import edu.wpi.teame.utilities.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class DatabaseEditorController {
  @FXML MFXButton backButton;

  @FXML TabPane tabPane;
  @FXML Tab editMapTab;

  @FXML Tab editDatabaseTab;

  @FXML DatabaseMapViewController mapViewController;

  @FXML
  public void initialize() {

    backButton.setOnMouseClicked(event -> Navigation.navigate(Screen.HOME));
    tabPane
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (observable, oldTab, newTab) -> {
              if (newTab == editMapTab) {
                mapViewController.loadFloorNodes(Floor.LOWER_TWO);
              }
            });
  }
}
