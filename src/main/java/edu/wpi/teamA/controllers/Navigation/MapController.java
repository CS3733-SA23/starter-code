package edu.wpi.teamA.controllers.Navigation;

import edu.wpi.teamA.navigation.Navigation;
import edu.wpi.teamA.navigation.Screen;
import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public class MapController implements IPageController {

  // @FXML private TableView mapData;
  @FXML private MFXButton importButton;
  @FXML private MFXButton exportButton;

  @FXML
  public void initialize() {}

  @Override
  public void back() {
    Navigation.navigate(Screen.HOME);
  }
}
