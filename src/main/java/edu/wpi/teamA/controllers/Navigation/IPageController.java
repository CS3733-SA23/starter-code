package edu.wpi.teamA.controllers.Navigation;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.fxml.FXML;

public interface IPageController {
  @FXML MFXButton backButton = new MFXButton("Back");

  @FXML
  public void initialize();

  public void back();
}
